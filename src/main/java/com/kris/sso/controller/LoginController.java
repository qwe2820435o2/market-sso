package com.kris.sso.controller;

import com.kris.sso.pojo.TaotaoResult;
import com.kris.sso.service.LoginService;
import com.kris.sso.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.kris.sso.utils.JsonUtils.objectToJson;

/**
 * 用户登录接口
 *
 * @author kris
 * @create 2017-01-12 15:11
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService mLoginService;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            TaotaoResult result = mLoginService.login(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        try {
            TaotaoResult result = mLoginService.getUserByToken(token);
            if (StringUtils.isNotBlank(callback)) {
                //请求为jsonp调用，需要支持
                //需要把result转换成字符串
                String json = objectToJson(result);
                return  callback+"("+json+");";
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

}
