package com.shk.manage;

import com.shk.entity.MbUserEntity;

import java.util.Map;

public interface UserServiceManage {
    public void regist(MbUserEntity mbUserEntity);

    public String md5PassSalt(String phone,String password);

    public Map<String,Object> login(MbUserEntity mbUserEntity);

    public Map<String,Object> getUser(String token);

    public Map<String, Object> userLoginOpenId(String openid);
}
