package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.AdminInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    @Select("select * from admin where account = #{account}")
    AdminInfo findAdminInfoByName(String account);
}
