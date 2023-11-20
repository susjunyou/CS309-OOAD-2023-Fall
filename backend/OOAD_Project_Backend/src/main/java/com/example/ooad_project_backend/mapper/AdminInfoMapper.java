package com.example.ooad_project_backend.mapper;

import com.example.ooad_project_backend.entity.AdminInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    @Select("select admin_password from admin where admin_account = #{account}")
    String findPasswordByName(String account);
}
