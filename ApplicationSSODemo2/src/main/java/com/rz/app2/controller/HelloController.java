package com.rz.app2.controller;

import com.rz.app2.domain.Auth;
import com.rz.sso.SSOClientContext;
import com.rz.sso.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by as on 2017/12/14.
 * request.getContextPath()：返回就是部署的应用名 比如这个项目就是 /app2
 */
@Component
@RequestMapping("/")
public class HelloController {

    private final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    @RequestMapping("/person")
    public String person(HttpServletRequest request) {
        ResultMsg resultMsg = SSOClientContext.getResultMsg();
        Auth auth = new Auth();
        auth.setGlobalId(resultMsg.getGlobalId());
        auth.setUid((Integer) resultMsg.getData().get("id"));
        auth.setUsername((String) resultMsg.getData().get("username"));
        request.getSession().setAttribute("user", auth);
        return "index";
    }
    @RequestMapping("/person/login")
    public String login(HttpServletRequest request) {
        ResultMsg resultMsg = SSOClientContext.getResultMsg();
        Auth auth = new Auth();
        auth.setGlobalId(resultMsg.getGlobalId());
        auth.setUid((Integer) resultMsg.getData().get("id"));
        auth.setUsername((String) resultMsg.getData().get("username"));
        request.getSession().setAttribute("user", auth);
        return "redirect:/";
    }

    @RequestMapping("/person/room")
    public String room() {
        return "room";
    }

}
