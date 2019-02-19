package com.shk.common.api;

import com.shk.constants.BaseApiConstants;
import com.sun.xml.internal.rngom.parse.host.Base;

import java.util.HashMap;
import java.util.Map;

public class BaseApiService {
    /**
     * 自定义返回成功
     * @return
     */
    public Map<String,Object> setResutSuccess(String msg){
        return setResut(BaseApiConstants.HTTP_200_CODE,msg,null);
    }

    /**
     * 自定义返回成功
     * @return
     */
    public Map<String,Object> setResutSuccess(){
        return setResut(BaseApiConstants.HTTP_200_CODE,BaseApiConstants.HTTP_SUCCESS_NAME,null);
    }
    public Map<String,Object> setResutSuccessData(Object data){
        return setResut(BaseApiConstants.HTTP_200_CODE,BaseApiConstants.HTTP_SUCCESS_NAME,data);
    }
    /**
     * 自定义返回错误
     * @param msg
     * @return
     */
    public Map<String,Object> setResutError(String msg){
        return setResut(BaseApiConstants.HTTP_500_CODE,msg,null);
    }
    /**
     * 参数错误
     * @return
     */
    public Map<String,Object> setResutParameterError(String msg){
        return setResut(BaseApiConstants.HTTP_400_CODE,BaseApiConstants.HTTP_SUCCESS_NAME,null);
    }
    /**
     * 自定义返回
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public Map<String,Object> setResut(Integer code, String msg, Object data){
        Map<String, Object> result = new HashMap<>();
        result.put(BaseApiConstants.HTTP_CODE_NAME,code);
        result.put(BaseApiConstants.HTTP_MSG_NAME,msg);
        if(data != null ){
            result.put(BaseApiConstants.HTTP_DATA_NAME,data);
        }

        return result;
    }
}
