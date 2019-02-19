package com.shk.manage.impl;

import com.alibaba.fastjson.JSONObject;
import com.shk.common.api.BaseApiService;
import com.shk.constants.DBTableName;
import com.shk.constants.MQInterfaceType;
import com.shk.constants.RedisContants;
import com.shk.dao.UserDao;
import com.shk.entity.MbUserEntity;
import com.shk.manage.UserServiceManage;
import com.shk.mq.produce.RegisterMailboxProducer;
import com.shk.redis.BaseRedisService;
import com.shk.token.TokenUtils;
import com.shk.utils.DateUtils;
import com.shk.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.util.Map;

@Service
@Slf4j
public class UserServiceManageImpl extends BaseApiService implements UserServiceManage {
    @Autowired
    private UserDao userDao;
    @Value("${messages.queue}")
    private String MESSAGES_QUEUE;

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private BaseRedisService baseRedisService;
    @Override
    public void regist(MbUserEntity mbUserEntity) {
        mbUserEntity.setCreated(DateUtils.getTimestamp());
        mbUserEntity.setUpdated(DateUtils.getTimestamp());
        mbUserEntity.setPassword(md5PassSalt(mbUserEntity.getPhone(), mbUserEntity.getPassword()));
        userDao.save(mbUserEntity, DBTableName.TABLE_MB_USER);
        //队列
        Destination activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
        //组装报文格式
        String json = mailMessage(mbUserEntity.getEmail(),mbUserEntity.getUsername());
        log.info("###regist() 发送邮件报文mailMessage:{}",json);
        registerMailboxProducer.sendMess(activeMQQueue,json);

    }

    public String mailMessage(String email ,String userName) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", MQInterfaceType.SMS_MAIL);
        JSONObject content = new JSONObject();
        content.put("mail",email);
        content.put("userName",userName);
        root.put("header",header);
        root.put("content",content);
        return root.toJSONString();
    }

    @Override
    public String md5PassSalt(String phone, String password) {
        {
            String newPass = MD5Util.MD5(phone+password);
            return newPass;
        }
    }

    @Override
    public Map<String,Object> login(MbUserEntity mbUserEntity) {
        //根据参数进行数据库查询
        String phone = mbUserEntity.getPhone();
        String password = mbUserEntity.getPassword();
        String newPassword = md5PassSalt(phone,password);
        MbUserEntity userPhoneAndPwd = userDao.getUserPhoneAndPwd(phone, newPassword);
        if(userPhoneAndPwd == null){
            return setResutError("账号或密码错误");
        }
       String token = setUsertoken(userPhoneAndPwd.getId());
        //返回token
        return setResutSuccessData(token);

    }

    @Override
    public Map<String, Object> getUser(String token) {
        //从redis中查询userid
        String userId = baseRedisService.get(token);
        if(StringUtils.isEmpty(userId)){
            return setResutError("用户已经过期！");
        }
        Long newUserId = Long.parseLong(userId);
        MbUserEntity user = userDao.getUser(newUserId);
        user.setPassword(null);
        return setResutSuccessData(user);
    }

    @Override
    public Map<String, Object> userLoginOpenId(String openid) {
     MbUserEntity mbUserEntity  = userDao.findUserOpenId(openid);
     if(mbUserEntity == null){
         return setResutError("没有关联用户");
     }
     //自动登入
       String token = setUsertoken(mbUserEntity.getId());
        return setResutSuccessData(token);
    }

    private String setUsertoken(Long id ){
        //生成对应的token
        String token = tokenUtils.getToken();
        //key为自定义令牌，用户的userId作为value存放在redis中
        baseRedisService.set(token,id+"", RedisContants.USER_TOKEN_TERMVALIDITY);
        return token;

    }
}
