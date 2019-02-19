package com.shk.dao;

import com.shk.entity.MbUserEntity;
import com.shk.mybatis.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserDao extends BaseDao {

@Select("select id,username,password,phone,email,created,updated from mb_user where phone=#{phone} and password = #{password}")
public MbUserEntity getUserPhoneAndPwd(@Param("phone")String phone,@Param("password") String password);

@Select("select id,username,password,phone,email,created,updated from mb_user where id=#{userid}")
public MbUserEntity getUser(@Param("userid")Long id);
@Select("select id,username,password,phone,email,created,updated from mb_user where openid=#{openid}")
public MbUserEntity findUserOpenId(@Param("openid") String openid);
}
