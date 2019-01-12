package com.rz.sso.utils;/**
 * Created by as on 2018/3/25.
 */

/**
 * @author as
 * @create 2018-03-25 19:55
 * @desc 生成key前缀的工具类
 */
public class KeyUtils {

    public static String getSessionKey(String sessionId) {
        return "session." + sessionId;
    }
}
