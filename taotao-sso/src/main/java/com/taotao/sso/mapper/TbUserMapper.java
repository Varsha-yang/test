package com.taotao.sso.mapper;
import com.taotao.sso.pojo.TbUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TbUserMapper {
    @Select("select * from tb_user where username = #{username}")
    public List<TbUser> checkUserName(@Param("username") String username);

    @Select("select * from tb_user where phone = #{phone}")
    public List<TbUser> checkPhone(@Param("phone") String phone);

    @Select("select * from tb_user where email = #{email}")
    public List<TbUser> checkEmail(@Param("email") String email);

    @Insert({"insert into tb_user(username,password,phone,email,created,updated)" +
            "values(#{tbUser.username},#{tbUser.password},#{tbUser.phone},#{tbUser.email},#{tbUser.created}," +
            "#{tbUser.updated})"})
    public void insertUser(@Param("tbUser") TbUser tbUser);


}
