package com.shk.service.impl;

import com.shk.api.service.DemoApiService;
import com.shk.common.api.BaseApiService;
import com.shk.redis.BaseRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class DemoApiServiceImpl extends BaseApiService implements DemoApiService {

    @Autowired
    private BaseRedisService baseRedisService;

    public Map<String, Object> demo() {

        return setResutSuccess();
    }

    @Override
    public Map<String, Object> setKey(String key, String value) {
        baseRedisService.setString(key,value);
        return setResutSuccess();
    }

    @Override
    public Map<String, Object> getKey(String key) {
        String value = baseRedisService.get(key);
        return  setResutSuccessData(value);
    }
}
