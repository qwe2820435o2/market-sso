package com.kris.sso.service;

import com.kris.sso.pojo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kris
 * @create 2017-01-12 14:16
 */
public interface LoginService {
    TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);
    TaotaoResult getUserByToken(String token);
}
