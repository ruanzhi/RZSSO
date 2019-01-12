package com.rz.sso.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by as on 2017/12/20.
 */
public class CookieUtil {

    public final static String TGC = "CASTGC";

    public static void delCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TGC)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response) {
        //默认会话级别的，浏览器关闭就失效
        Cookie cookie = new Cookie(TGC, request.getSession().getId());
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (null != cookies) {
            for (Cookie c : cookies) {
                if (c.getName().equals(TGC)) {
                    cookie = c;
                    break;
                }
            }
        }
        return cookie;
    }
}
