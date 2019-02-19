package com.shk.mybatis;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

public interface BaseDao {
    /*@InsertProvider注解的作用，自定义sql语句*/
    @InsertProvider(type = BaseProvider.class,method="save")
    public void save(@Param("oj") Object oj,@Param("table") String table);


}
