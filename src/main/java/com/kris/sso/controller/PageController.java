package com.kris.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
     *
     * @return
     */
    @RequestMapping("/page/login")
    public String showLogin(String redirectURL, Model model) {
        //需要把参数传递给jsp
        model.addAttribute("redirect", redirectURL);
        return "login";
    }

    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }
}
