package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherInfoMapper extends BaseMapper<TeacherInfo> {
    @Select("select * from teacher where account = #{account}")
    TeacherInfo findTeacherInfoByAccount(String account);
}
