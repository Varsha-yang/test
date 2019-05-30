package com.taotao.sso.service;
import com.taotao.sso.pojo.ResponseResult;
import com.taotao.sso.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UserService {

    public ResponseResult checkData(String content, String type);
    public ResponseResult createUser(TbUser tbUser);
    public ResponseResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);
    public ResponseResult getUserByToken(String token);
}
