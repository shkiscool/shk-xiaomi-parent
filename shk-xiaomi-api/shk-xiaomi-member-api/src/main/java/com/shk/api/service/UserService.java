package com.shk.api.service;

import com.shk.entity.MbUserEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/member")
public interface UserService {
    /**
     * 注册服务
     * @param mbUserEntity
     * @return
     */
    @PostMapping("/regist")
    public Map<String,Object> regist(@RequestBody MbUserEntity mbUserEntity);

    /**
     * 登入服务
     * 登入成功后 生成对应的token 作为key 将用户userId作为value存放在redis中，返回token给客户端
     */
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody MbUserEntity mbUserEntity);

    /**
     * 使用token查找用户信息
     * @param token
     * @return
     */
    @PostMapping("/getUser")
    public Map<String,Object> getUser(@RequestParam("token") String token);

    /**
     * 使用token查找用户信息
     * @param token
     * @return
     */
    @PostMapping("/userLoginOpenId")
    public Map<String,Object> userLoginOpenId(@RequestParam("openid") String openid);
}
