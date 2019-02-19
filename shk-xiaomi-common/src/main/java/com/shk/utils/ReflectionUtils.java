package com.shk.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Timestamp;


/**
 * java反射工具类
 */
@Slf4j
public class ReflectionUtils {


    /**
     * 获取类的属性，拼接成字符串
     * @param oj
     * @return
     */
    public static String getInsertFileds(Object oj){
        if(oj == null){
            return null;
        }
        Class clazz = oj.getClass();
        //获取子类的属性值
        Field[] sonDeclaredFields = clazz.getDeclaredFields();
        String sonFields = appendFields(sonDeclaredFields);
        //获取父类的属性值
        Class superClazz = clazz.getSuperclass();
        Field[] superDeclaredFields = superClazz.getDeclaredFields();
        String superFields = appendFields(superDeclaredFields);
        return sonFields+","+superFields;

    }

    /**
     * 获取类的属性对于的值进行拼接
     * @param oj
     * @return
     */
    public static String getInsertFiledsValue(Object oj){
        if(oj == null){
            return null;
        }
        Class clazz = oj.getClass();
        //获取所有的属性对于的值
        Field[] sondeclaredFields = clazz.getDeclaredFields();
        String sonFieldsValue = appendFieldValues(sondeclaredFields,oj);
        Class superClazz = clazz.getSuperclass();
        Field[] superdeclaredFields = superClazz.getDeclaredFields();
        String superFieldValue = appendFieldValues(superdeclaredFields,oj);
        return  sonFieldsValue+","+superFieldValue;
    }

    private static String appendFieldValues(Field[] declaredFields, Object oj) {
     StringBuffer sf = new StringBuffer();
        try {
            for(int i = 0; i<declaredFields.length;i++){
                Field field = declaredFields[i];
                String name = field.getName();
                if(name == "id"){
                    continue;
                }
                //设置私有权限访问
                field.setAccessible(true);
                boolean falg = false;
                if( (field.get(oj) != null) && ((field.get(oj) instanceof String)||(field.get(oj) instanceof Timestamp)) ){
                    falg = true;
                }
                sf.append(falg?"'"+field.get(oj)+"'":field.get(oj));
                if(i<declaredFields.length-1){
                    sf.append(",");
                }
            }
        } catch (Exception e) {
            log.error("###ERROR:getDeclareFieldsValues方法出现异常:",e);
        }
        return sf.toString();
    }

    /**
     * 获取类的属性进行拼接返回
     * @param declaredFields
     * @return
     */
    private static String appendFields(Field[] declaredFields) {
      StringBuffer sf = new StringBuffer();
      //获取子类属性
        for(int i =0; i<declaredFields.length; i++){
            Field field = declaredFields[i];
            String name = field.getName();
            //判断属性是不是等于ID 是的话就不加入到拼接的字符串
            if(name == "id"){
                continue;
            }
            sf.append(name);
            //判断属性是不是最后一个
            if(i < declaredFields.length-1){
                sf.append(",");
            }
        }
        return sf.toString();
    }

    /*public static void main(String[] args){
        MbUser mbUser = new MbUser();
        mbUser.setUsername("锟哥");
        mbUser.setEmail("572728626@qq.com");
        mbUser.setPassword("123456");
        mbUser.setPhone("18659175074");
        mbUser.setCreated(DateUtils.getTimestamp());
        mbUser.setUpdated(DateUtils.getTimestamp());
        String filed = getInsertFileds(mbUser);
        String value = getInsertFiledsValue(mbUser);
        System.out.println(filed);
        System.out.println(value);
        SQL sql = new SQL() {
            {

                INSERT_INTO("mb_user");
                VALUES(filed,value);

            }
        };
       System.out.println(sql.toString());
    }*/
}

