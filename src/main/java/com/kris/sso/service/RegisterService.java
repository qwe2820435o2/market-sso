package com.kris.sso.service;

import com.kris.sso.pojo.TaotaoResult;
import com.kris.sso.pojo.TbUser;

/**
 * @author kris
 * @create 2017-01-11 17:18
 */
public interface RegisterService {
    TaotaoResult checkData(String param,int type);
    TaotaoResult register(TbUser user);
}
