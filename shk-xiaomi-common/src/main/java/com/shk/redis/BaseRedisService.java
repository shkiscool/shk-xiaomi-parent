package com.shk.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
@Slf4j
public class BaseRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setString(String key,Object value,Long timeOut){
        set(key,value,timeOut);

    }
    public void setString(String key,Object value){
        set(key,value,null);

    }

    public  void set(String key,Object value,Long timeOut){
        if(value instanceof String || value instanceof Long){
            String setValue = (String)value;
            log.info("redis保存的key:{}value:{}",key,setValue);
            stringRedisTemplate.opsForValue().set(key,setValue);
        }
        //设置有效期
        if(timeOut != null){
            stringRedisTemplate.expire(key,timeOut, TimeUnit.SECONDS);
        }
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delete (String key) {
      stringRedisTemplate.delete(key);
    }
}
