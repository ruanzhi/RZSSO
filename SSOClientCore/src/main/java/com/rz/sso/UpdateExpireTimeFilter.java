package com.rz.sso;

import com.ning.http.client.AsyncHttpClient;
import com.rz.sso.Utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author as
 * @create 2018-03-25 10:17
 * @desc 更新登录过期时间
 */
public class UpdateExpireTimeFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(UpdateExpireTimeFilter.class);

    private String SSO_BASE_URL;

    public void init(FilterConfig filterConfig) throws ServletException {
        SSO_BASE_URL = filterConfig.getInitParameter("SSO_BASE_URL");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //得到局部会话Session
        HttpSession session = request.getSession();
        //是否通过拦截器
        if (session.getAttribute("auth") != null) {//已经登录，每次请求异步更新登录过期时间
            AsyncHttpClient client = new AsyncHttpClient();
            ResultMsg resultMsg = (ResultMsg) session.getAttribute("auth");
            client.prepareGet(SSO_BASE_URL + "/updateExpireTime?globalId=" + resultMsg.getGlobalId()).execute();
        }
        filterChain.doFilter(request, response);
    }

    public void destroy() {

    }
}
