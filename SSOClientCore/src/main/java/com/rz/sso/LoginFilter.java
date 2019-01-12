package com.rz.sso;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rz.sso.Utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by as on 2018/2/25.
 */
public class LoginFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private String SSO_BASE_URL;

    private String LOGIN_PAGE_URL;

    private String VERIFY_URL;

    private String APPLICATION_SERVER;


    public void init(FilterConfig filterConfig) throws ServletException {
        SSO_BASE_URL = filterConfig.getInitParameter("SSO_BASE_URL");
        LOGIN_PAGE_URL = "/loginPage";
        VERIFY_URL = "/verify";
        APPLICATION_SERVER = filterConfig.getInitParameter("APPLICATION_SERVER");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //得到局部会话Session
        HttpSession session = request.getSession();
        //是否通过拦截器
        boolean passInterceptor = true;
        if (session.getAttribute("auth") == null) {//没有登录
            //判断路径里面有没有tk
            String token = request.getParameter("ticket");
            if (token == null) {//没有访问过认证中心
                passInterceptor = false;
            } else {
                logger.info("应用接收到的token:{}", token);
                try {
                    String result = HttpClientUtil.doGet(getToSSOVERTIFY(token, request));
                    System.out.println(result);
                    passInterceptor = saveUserInfo(result, request);
                } catch (Exception e) {
                    e.printStackTrace();
                    passInterceptor = false;
                }
            }
        }
        //没有通过拦截器，直接前往sso登录页面
        if (!passInterceptor) {
            response.sendRedirect(getToSSOLoginPage(request.getRequestURL().toString()));
            return;
        }
        //与当前线程绑定，设置值
        SSOClientContext.setResultMsg((ResultMsg) request.getSession().getAttribute("auth"));
        filterChain.doFilter(request, response);
    }

    /**
     * 得到前往SSO登录界面的URL
     *
     * @param callbackURL 成功之后回调的URL
     * @return 接口URL
     */
    public String getToSSOLoginPage(String callbackURL) {
        StringBuffer toSSOLoginPageURL = new StringBuffer();
        toSSOLoginPageURL.append(SSO_BASE_URL).append(LOGIN_PAGE_URL).append("?service=").append(callbackURL);
        logger.info("SSO登录界面的URL:{}", toSSOLoginPageURL.toString());
        return toSSOLoginPageURL.toString();
    }

    /**
     * 得到向SSO校验接口发送请求，校验票据
     *
     * @param token   校验票据
     * @param request HttpServletRequest对象
     * @return 接口URL
     */
    public String getToSSOVERTIFY(String token, HttpServletRequest request) {
        StringBuffer toSSOVERTIFYURL = new StringBuffer();
        String APPLICATION_LOGOUT_URL = APPLICATION_SERVER + "/" + request.getContextPath() + "/auth/logout";

        toSSOVERTIFYURL.append(SSO_BASE_URL).append(VERIFY_URL).append("?token=").append(token)
                .append("&localId=").append(request.getSession().getId())
                .append("&logoutUrl=").append(APPLICATION_LOGOUT_URL);
        logger.info("SSO校验票据的URL:{}", toSSOVERTIFYURL.toString());
        return toSSOVERTIFYURL.toString();
    }

    /**
     * @param result  SSO 以json格式 返回用户相关结果信息
     * @param request HttpServletRequest对象
     * @return 是否成功获取到用户信息，并保存
     * @throws IOException
     */
    public boolean saveUserInfo(String result, HttpServletRequest request) throws IOException {
        boolean checkPass = false;
        ObjectMapper objectMapper = new ObjectMapper();
        ResultMsg resultMsg = objectMapper.readValue(result, ResultMsg.class);
        logger.info("SSO系统返回的JSON格式用户信息:{}", resultMsg);
        if ("0".equals(resultMsg.getRet())) {//校验成功
            request.getSession().setAttribute("auth", resultMsg);
            checkPass = true;
        }
        return checkPass;
    }


    public void destroy() {

    }
}
