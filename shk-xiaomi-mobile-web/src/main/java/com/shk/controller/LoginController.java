package com.shk.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import com.shk.base.controller.BaseController;
import com.shk.constants.BaseApiConstants;
import com.shk.constants.WebContants;
import com.shk.entity.MbUserEntity;
import com.shk.feign.MbUserFeign;
import com.shk.web.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
@Controller
@Slf4j
public class LoginController extends BaseController {
    private static final String LOGIN = "login";
    private static final String INDEX = "index";
    private static final String ERROR = "error";
    private static final String ASSOCIATEDACCOUNT = "associatedAccount";
 @Autowired
 private MbUserFeign mbUserFeign;
 @RequestMapping("/locaLogin")
    public String locaLogin(){
     return LOGIN;
 }
 @RequestMapping("/login")
 public String login(MbUserEntity mbUserEntity, HttpServletRequest request, HttpServletResponse response){
     try {
         Map<String,Object> loginMap = mbUserFeign.login(mbUserEntity);
         Integer code = (Integer) loginMap.get(BaseApiConstants.HTTP_CODE_NAME);
         if(!code.equals(BaseApiConstants.HTTP_200_CODE)){
             String  msg = (String) loginMap.get("msg");
             return setError(request,msg,LOGIN);
         }
         //登入成功之后 获取token将token存放在cookie
         String token = (String) loginMap.get("data");
         CookieUtil.addCookie(response, WebContants.USER_TOKEN,token,WebContants.USER_TOKEN_TERMVALIDITY);
         return INDEX;
     } catch (Exception e) {
         e.printStackTrace();
         log.info("####登入出现异常####");
         return setError(request,"登入失败！",LOGIN);
     }

 }
 @RequestMapping("/authorizeUrl")
    public String authorizeUrl(HttpServletRequest request) throws QQConnectException {
     String authorizeURL = new Oauth().getAuthorizeURL(request);
     return "redirect:"+authorizeURL;
 }

 @RequestMapping("/qqLoginCallback")
 public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws QQConnectException {
     //第一步 获取授权码
     //第二步 获取accesstoken
     AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
     String accessToken = accessTokenObj.getAccessToken();
     if(StringUtils.isEmpty(accessToken)){
         return setError(request,"QQ授权失败!",ERROR);
     }
     //第三步 获取openid
      OpenID openIDObj = new OpenID(accessToken);
      String openID = openIDObj.getUserOpenID();
     if(StringUtils.isEmpty(openID)){
         return setError(request,"QQ授权失败!",ERROR);
     }

     //查询数据库openid是否关联，如果没有关联跳转到关联账号页面，如果有直接登入
      Map<String,Object > loginMap = mbUserFeign.userLoginOpenId(openID);
     Integer code = (Integer) loginMap.get(BaseApiConstants.HTTP_CODE_NAME);
     if(code.equals(BaseApiConstants.HTTP_200_CODE)){
         String  token  = (String) loginMap.get("data");
         CookieUtil.addCookie(response, WebContants.USER_TOKEN,token,WebContants.USER_TOKEN_TERMVALIDITY);
         return "redirect:/"+INDEX;
     }
     //没有关联qq账号
     session.setAttribute(WebContants.USER_SESSION_TOKEN,openID);
    return  ASSOCIATEDACCOUNT;
 }
}
