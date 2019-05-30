package com.taotao.sso.controller;


import com.taotao.sso.Units.ExceptionUtils;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.pojo.ResponseResult;
import com.taotao.sso.pojo.TbUser;
import com.taotao.sso.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sso/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public ResponseResult checkData(@PathVariable String param, @PathVariable String type) {
        ResponseResult result = null;
        if (StringUtils.isBlank(param)) {
            result = new ResponseResult(400, "校验内容不能为空");
        }
        if (StringUtils.isBlank(type)) {
            result = new ResponseResult(400, "校验类型不能为空");
        }
        if (!"1".equals(type) && !"2".equals(type) && !"3".equals(type)) {
            result = new ResponseResult(400, "校验内容是1，2，3以外");
        }
        if (result != null) {
            return result;
        }

        ResponseResult responseResult = null;
        try {
            responseResult = userService.checkData(param, type);
        } catch (Exception e) {
            responseResult = new ResponseResult(500, ExceptionUtils.getStackTrace(e));
        }
        return responseResult;

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult registerUser(TbUser tbUser) {
        try {
            ResponseResult responseResult = userService.createUser(tbUser);
            return responseResult;
        } catch (Exception e) {
            return new ResponseResult(500, ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            ResponseResult responseResult = userService.login(username, password, request, response);
            return responseResult;
        } catch (Exception e) {
            return new ResponseResult(500, ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        ResponseResult responseResult = null;
        try {
            responseResult = userService.getUserByToken(token);
        } catch (Exception e) {
            responseResult = new ResponseResult(500, ExceptionUtils.getStackTrace(e));
        }
        if (StringUtils.isBlank(callback)) {
            return responseResult;
        } else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(responseResult);

            return mappingJacksonValue;
        }
    }
}
