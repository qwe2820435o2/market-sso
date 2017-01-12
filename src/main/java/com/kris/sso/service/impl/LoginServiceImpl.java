package com.kris.sso.service.impl;

import com.kris.sso.component.JedisClient;
import com.kris.sso.mapper.TbUserMapper;
import com.kris.sso.pojo.TaotaoResult;
import com.kris.sso.pojo.TbUser;
import com.kris.sso.pojo.TbUserExample;
import com.kris.sso.service.LoginService;
import com.kris.sso.utils.CookieUtils;
import com.kris.sso.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author kris
 * @create 2017-01-12 14:38
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private TbUserMapper mUserMapper;
    @Autowired
    private JedisClient mJedisClient;

    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private int SESSION_EXPIRE;

    @Override
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        //校验用户名密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = mUserMapper.selectByExample(example);
        //取用户信息
        if (list == null || list.isEmpty()) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
        //校验密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        //登录成功
        //生成token
        String token = UUID.randomUUID().toString();
        //把用户信息写入redis
        //key:REDIS_SESSION:{TOKEN}
        //value:user转json
        mJedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        mJedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
        //写cookie
        CookieUtils.setCookie(request,response,"MARKET_TOKEN",token);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        //根据token取用户信息
        String json = mJedisClient.get(REDIS_SESSION_KEY + ":" + token);
        //判断是否查询到结果
        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "用户session已经过期");
        }
        //把json转换成java对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        //更新session的过期时间
        mJedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);

        return TaotaoResult.ok(user);
    }
}
