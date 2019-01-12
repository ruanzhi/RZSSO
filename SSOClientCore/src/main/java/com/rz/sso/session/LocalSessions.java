package com.rz.sso.session;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by as on 2017/12/16.
 */
public class LocalSessions {
    //存放所有全局会话
    private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

    public static void addSession(String sessionId, HttpSession session) {
        sessions.put(sessionId, session);
    }

    public static void delSession(String sessionId) {
        sessions.remove(sessionId);
    }
    //根据id得到session
    public static HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

}
