package com.example.ooad_project_backend.mapper;

import com.example.ooad_project_backend.entity.AdminInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    @Select("select * from admin where name = #{name} and password = #{password}")
    AdminInfo findByNameAndPass(String name, String password);
}
