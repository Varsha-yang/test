package com.taotao.sso.service.impl;

import com.taotao.sso.Units.CookieUtils;
import com.taotao.sso.Units.Jsonutils;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.mapper.TbUserMapper;
import com.taotao.sso.pojo.ResponseResult;
import com.taotao.sso.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Resource
    private JedisClient ss;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;


    public static final String COOKIE_NAME = "TT_TOKEN";

    public ResponseResult checkData(String content, String type) {
        List<TbUser> list = new ArrayList<TbUser>();
        if ("1".equals(type)) {
            // username的check
            list = tbUserMapper.checkUserName(content);

        } else if ("2".equals(type)) {
            // phone的check
            list = tbUserMapper.checkPhone(content);
        } else {
            // email的check
            list = tbUserMapper.checkEmail(content);
        }
        if (list != null && list.size() > 0) {
            return new ResponseResult(false);
        }
        return new ResponseResult(true);

    }


    public ResponseResult createUser(TbUser tbUser) {

        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        tbUserMapper.insertUser(tbUser);
        return new ResponseResult(200, "注册成功");
    }


    public ResponseResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        List<TbUser> list = tbUserMapper.checkUserName(username);
        if (list != null && list.size() > 0) {
            TbUser tbUser = list.get(0);
            if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())) {
                tbUser.setPassword(null);
                String token = UUID.randomUUID().toString();
                ss.set(REDIS_USER_SESSION_KEY + ":" + token, Jsonutils.objectToJson(tbUser));
                ss.expire(REDIS_USER_SESSION_KEY + ":" + token, 60);
                CookieUtils.setCookie(request, response, COOKIE_NAME, token);
                return new ResponseResult(500, "登录成功", token);
            } else {
                return new ResponseResult(500, "密码不正确", null);
            }
        } else {
            return new ResponseResult(500, "用户名不存在", null);
        }
    }


    public ResponseResult getUserByToken(String token) {
        String json = ss.get(REDIS_USER_SESSION_KEY + ":" + token);

        if (StringUtils.isBlank(json)) {
            return new ResponseResult(400, "信息已经过期,请重新登陆");
        } else {
            ss.expire(REDIS_USER_SESSION_KEY + ":" + token, 60);
        }
        return new ResponseResult(200, null, Jsonutils.jsonToPojo(json, TbUser.class));
    }

}
