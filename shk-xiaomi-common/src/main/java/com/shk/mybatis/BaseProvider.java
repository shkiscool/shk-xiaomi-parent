package com.shk.mybatis;

import com.shk.utils.ReflectionUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * 自定义封装sql语句
 */
public class BaseProvider {

    public String save(Map<String,Object> map){
        //实体类
        Object oj = map.get("oj");
        //表名
        String table = (String) map.get("table");
        //生成添加的sql语句 使用反射机制
        //步骤：使用反射机制加载这个类的所有属性
        SQL sql = new SQL(){
            {
                INSERT_INTO(table);
                VALUES(ReflectionUtils.getInsertFileds(oj),ReflectionUtils.getInsertFiledsValue(oj));
            }
        };
        return sql.toString();
    }



}
