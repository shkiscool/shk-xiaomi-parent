package com.shk.token;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class TokenUtils {
    public  String getToken(){
        return UUID.randomUUID().toString();
    }
}
