package com.rz.app1.controller;


import com.rz.app1.domain.Auth;
import com.rz.sso.ResultMsg;
import com.rz.sso.SSOClientContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by as on 2017/12/14.
 * request.getContextPath()：返回就是部署的应用名 比如这个项目就是 /app2
 * <p>
 * request.getSession(true)：若存在会话则返回该会话，否则新建一个会话。
 * <p>
 * request.getSession(false)：若存在会话则返回该会话，否则返回NULL
 */
@Component
@RequestMapping("/")
public class HelloController {


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
