package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {
    @Select("select * from student where account = #{account}")
    StudentInfo findStudentInfoByName(String account);

    @Select("select * from student_Info where name = #{name}")
    TeacherInfo findByName(@Param("name") String name);
}
