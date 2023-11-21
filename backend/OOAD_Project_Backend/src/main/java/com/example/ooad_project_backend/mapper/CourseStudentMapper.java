package com.example.ooad_project_backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseStudentMapper {

    @Select("select course_id from course_student where student_id = #{studentId}")
    List<Integer> findCourseIdByStudentId(Integer studentId);
}
