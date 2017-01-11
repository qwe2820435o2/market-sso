package com.kris.sso.controller;

import com.kris.sso.pojo.TaotaoResult;
import com.kris.sso.service.RegisterService;
import com.kris.sso.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.kris.sso.utils.JsonUtils.objectToJson;

/**
 * 数据校验接口，校验是否可注册
 *
 * @author kris
 * @create 2017-01-11 17:28
 */
@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private RegisterService mRegisterService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
        try {
            TaotaoResult result = mRegisterService.checkData(param, type);
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
