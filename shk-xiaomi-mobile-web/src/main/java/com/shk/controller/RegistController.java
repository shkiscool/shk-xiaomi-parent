package com.shk.controller;

import com.shk.base.controller.BaseController;
import com.shk.constants.BaseApiConstants;
import com.shk.entity.MbUserEntity;
import com.shk.feign.MbUserFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Slf4j
@Controller
public class RegistController extends BaseController {
private static final String LOCAREGIST = "locaRegist";
private static final String LOGIN = "login";

@Autowired
private MbUserFeign mbUserFeign;
@RequestMapping("locaregist")
public String getLocaRegist(){
    return LOCAREGIST;
}

@RequestMapping(value ="/regist",method = RequestMethod.POST)
public String  regist(MbUserEntity mbUserEntity, HttpServletRequest request){
    try {
        Map<String,Object> registMap =  mbUserFeign.regist(mbUserEntity);
        Integer code = (Integer) registMap.get(BaseApiConstants.HTTP_CODE_NAME);
        if(!code.equals(BaseApiConstants.HTTP_200_CODE)){
            String  msg = (String) registMap.get("msg");
            return setError(request,msg,LOCAREGIST);
        }
        return LOGIN;
    } catch (Exception e) {
        log.info("####注册出现异常####");
        return setError(request,"注册失败！",LOCAREGIST);
    }

}

}
