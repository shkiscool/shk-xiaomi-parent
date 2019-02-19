package com.shk.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.shk.constants.BaseApiConstants;
import com.shk.entity.MbUserEntity;
import com.shk.feign.MbUserFeign;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class BaseController {
    @Autowired
    private MbUserFeign mbUserFeign;

    public MbUserEntity getMbUserEntity(String token){
        Map<String, Object> userMap = mbUserFeign.getUser(token);
        Integer code  = (Integer) userMap.get(BaseApiConstants.HTTP_CODE_NAME);
        if(!code.equals(BaseApiConstants.HTTP_200_CODE)){
            return  null;
        }
        //获取data返回值
        LinkedHashMap linkedHashMap= (LinkedHashMap) userMap.get(BaseApiConstants.HTTP_DATA_NAME);
        String json =  new JSONObject().toJSONString(linkedHashMap);
        MbUserEntity o = new JSONObject().parseObject(json, MbUserEntity.class);
        return o;
    }
    public String setError(HttpServletRequest request, String msg, String addres){
        request.setAttribute("error",msg);
        return addres;
    }
}
