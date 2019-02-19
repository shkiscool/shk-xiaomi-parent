package com.shk.service.impl;

import com.shk.api.service.UserService;
import com.shk.common.api.BaseApiService;
import com.shk.entity.MbUserEntity;
import com.shk.manage.UserServiceManage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@Slf4j
@RestController
public class UserServiceImpl extends BaseApiService implements UserService {
    @Autowired
    private UserServiceManage userServiceManage;
    @Override
    public Map<String, Object> regist(@RequestBody MbUserEntity mbUserEntity) {
        if(StringUtils.isEmpty(mbUserEntity.getUsername())){
            return setResutParameterError("用户名称不能为空!");
        }
        if(StringUtils.isEmpty(mbUserEntity.getUsername())){
            return setResutParameterError("密码不能为空!");
        }
        try {
            userServiceManage.regist(mbUserEntity);
            return setResutSuccess();
        } catch (Exception e) {
            log.error("###regist()ERROR:",e);
            return setResutError("注册失败!");
        }
    }

    @Override
    public Map<String, Object> login(@RequestBody  MbUserEntity mbUserEntity) {
     return userServiceManage.login(mbUserEntity);
    }

    @Override
    public Map<String, Object> getUser(@RequestParam("token") String token) {
        if(StringUtils.isEmpty(token)){
            return setResutParameterError("token不能为空");
        }
       return userServiceManage.getUser(token);

    }

    @Override
    public Map<String, Object> userLoginOpenId(@RequestParam("openid")String openid) {
        return userServiceManage.userLoginOpenId(openid);
    }
}
