package com.rz.sso;

import com.rz.sso.Utils.HttpClientUtil;
import com.rz.sso.session.LocalSessions;
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
public class LogoutFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(LogoutFilter.class);

    private String SSO_BASE_URL;

    private String LOGOUT_URL;

    public void init(FilterConfig filterConfig) throws ServletException {
        SSO_BASE_URL = filterConfig.getInitParameter("SSO_BASE_URL");
        LOGOUT_URL = "/logout";
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //得到局部会话Session
        String localId = request.getParameter("localId");
        try {
            if (localId != null) {
                HttpSession session = LocalSessions.getSession(localId);//获取本地会话Session
                if (session != null) {
                    session.invalidate();//销毁会话
                }
                response.getWriter().write("OK");
            } else {
                String logout = getToSSOLogout(request);
                if (logout == null) {
                    response.sendRedirect(request.getContextPath());//重定向到首页
                }
                String result = HttpClientUtil.doGet(logout);//这里已经代表删除所有会话
                if ("OK".equals(result)) {
                    response.sendRedirect(request.getContextPath());//重定向到首页
                } else {
                    response.getWriter().write("logout fail");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * 得到SSO退出的接口
     *
     * @param request
     * @return
     */
    private String getToSSOLogout(HttpServletRequest request) {
        ResultMsg resultMsg = (ResultMsg) request.getSession().getAttribute("auth");
        if (resultMsg != null) {
            StringBuffer toSSOLogoutURL = new StringBuffer();
            toSSOLogoutURL.append(SSO_BASE_URL).append(LOGOUT_URL).append("?gId=").append(resultMsg.getGlobalId());
            logger.info("SSO登出接口的URL:{}", toSSOLogoutURL.toString());
            return toSSOLogoutURL.toString();
        }
        return null;
    }

    public void destroy() {

    }
}
