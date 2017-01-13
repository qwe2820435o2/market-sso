package com.kris.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面展示接口
 *
 * @author kris
 * @create 2017-01-13 9:42
 */
@Controller
public class PageController {

    /**
     * 展示登录页面
     * @return
     */
    @RequestMapping("/page/login")
    public String showLogin(){
        return "login";
    }

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }
}
