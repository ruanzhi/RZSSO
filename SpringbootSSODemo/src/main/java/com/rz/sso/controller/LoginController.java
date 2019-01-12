package com.rz.sso.controller;

import com.rz.sso.domain.ResultMsg;
import com.rz.sso.domain.TokenInfo;
import com.rz.sso.service.GlobalSessionsService;
import com.rz.sso.service.RegisterService;
import com.rz.sso.service.TokenService;
import com.rz.sso.service.UserService;
import com.rz.sso.utils.CookieUtil;
import com.rz.sso.utils.HttpClientUtil;
import com.rz.sso.utils.ResultMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

/**
 * Created by as on 2017/12/14.
 * 用户到认证中心登录后，用户和认证中心之间建立起了会话，我们把这个会话称为全局会话
 * 系统应用和用户浏览器之间建立起局部会话
 */
@Controller
@RequestMapping("/")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;//获取配置文件中的服务
    @Autowired
    private GlobalSessionsService globalSessionsService;

    /**
     * 跳转到登录页面
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/loginPage")
    public String toLoginPage(HttpServletRequest request, ModelMap map) {
        String service = request.getParameter("service");
        Cookie cookie = CookieUtil.getCookie(request);  //获取会话认证系统这个域下面的cookie
        if (cookie != null && globalSessionsService.getGlobalSession(cookie.getValue()) != null) {      //判断是否当前会话之前已经认证过了
            return "redirect:" + service + "?ticket=" + createTokenAndSaveTokenInfo(request, service, null);
        }
        map.put("service", service);
        String customLoginPage = env.getProperty(getHostName(service) + ".login-page-name");
        return StringUtils.isEmpty(customLoginPage) == true ? "login" : customLoginPage;

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(String service, HttpServletRequest request, HttpServletResponse response) {
        //查询是否存在此用户
        Map<String, Object> result = userService.checkUserByConfigSql(getHostName(service), request.getParameterMap());
        if (result != null) {
            CookieUtil.addCookie(request, response);// 保存全局会话到cookie
            if (service != null) {
                return service + "?ticket=" + createTokenAndSaveTokenInfo(request, service, result);
            }
        }
        //默认到认证中心欢迎页面
        return "fail";
    }

    /**
     * 解析域名
     *
     * @param service
     * @return
     */
    public String getHostName(String service) {
        try {
            URL url = new URL(service);
            return url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String createTokenAndSaveTokenInfo(HttpServletRequest request, String service, Map<String, Object> result) {
        //生成一个token
        String token = UUID.randomUUID().toString();
        TokenInfo newTokenInfo = new TokenInfo();
        TokenInfo tokenInfo = globalSessionsService.getGlobalSession(request.getSession().getId());
        if (tokenInfo != null) {
            newTokenInfo.setData(tokenInfo.getData());
            newTokenInfo.setSsoClient(service);
            newTokenInfo.setGlobalId(tokenInfo.getGlobalId());
            tokenService.setToken(token, newTokenInfo);
        } else {
            newTokenInfo.setData(result);
            newTokenInfo.setSsoClient(service);
            newTokenInfo.setGlobalId(request.getSession().getId());
            tokenService.setToken(token, newTokenInfo);
            //保存全局会话
            globalSessionsService.saveGlobalSession(request.getSession().getId(), newTokenInfo);
            //request.getSession().setAttribute(request.getSession().getId(), newTokenInfo);//全局会话
        }
        return token;
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg verify(String token, String localId, String logoutUrl, HttpServletRequest request) {
        TokenInfo tokenInfo = tokenService.getToken(token);//认证token是否有效
        ResultMsg resultMsg = null;
        if (tokenInfo != null) {//有效
            registerService.registerAppLogoutURL(tokenInfo.getGlobalId(), localId, logoutUrl);
            resultMsg = ResultMsgUtil.success(tokenInfo);
        } else {
            resultMsg = ResultMsgUtil.fail();
        }
        tokenService.delToken(token);
        logger.info("返回结果:{}", resultMsg);
        return resultMsg;
    }

    /**
     * 更新全局会话时间
     *
     * @param globalId
     */
    @RequestMapping(value = "/updateExpireTime")
    @ResponseBody
    public String updateExpireTime(String globalId) {
        //设置过期时间
        globalSessionsService.updatetGlobalSessionIdExpireTime(globalId);
        return "ok";
    }

    @RequestMapping(value = "/logout")
    public void logout(String gId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tmpGlobalId = gId;
        if (tmpGlobalId == null) {
            tmpGlobalId = request.getSession().getId();
        }
        try {
            boolean success = logoutAllApp(tmpGlobalId);//所有本地会话都销毁成功
            if (success) {//销毁全局session和cookie
                CookieUtil.delCookie(request, response);
                //删除全局会话
                globalSessionsService.deleteGlobalSession(tmpGlobalId);
                //删除注册好的路径
                registerService.delAppLogoutURL(tmpGlobalId);
                logger.info("登出成功");
                if (gId != null) {//应用重定向登出,向调用端输出结果值
                    response.getWriter().write("OK");
                } else {////从认证中心登出，重定向到认证中心主页
                    response.sendRedirect(request.getContextPath() + "/welcome");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail");
        }
    }

    public boolean logoutAllApp(String sessionId) throws Exception {
        boolean success = true;
        Map<String, String> registerAppInfo = registerService.getAppLogoutURL(sessionId);
        for (Map.Entry<String, String> entry : registerAppInfo.entrySet()) {
            String localId = entry.getKey();
            String appLogoutURL = entry.getValue();
            String result = HttpClientUtil.doGet(appLogoutURL + "?localId=" + localId);
            if (!"OK".equals(result)) {
                logger.info("此应用URL登出调用失败:{}?localId={}", appLogoutURL, localId);
                success = false;
                break;
            }
        }
        return success;
    }

}
