package com.rz.sso.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by as on 2017/12/16.
 */

public class LocalSessionListener implements HttpSessionListener {
    private final static Logger logger = LoggerFactory.getLogger(LocalSessionListener.class);

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("建立了会话session:{}", httpSessionEvent.getSession().getId());
        LocalSessions.addSession(httpSessionEvent.getSession().getId(), httpSessionEvent.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("销毁会话session:{}", httpSessionEvent.getSession().getId());
        LocalSessions.delSession(httpSessionEvent.getSession().getId());
    }
}
