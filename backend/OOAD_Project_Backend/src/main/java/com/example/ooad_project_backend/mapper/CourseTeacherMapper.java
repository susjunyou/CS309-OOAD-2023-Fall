package com.example.ooad_project_backend.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseTeacherMapper{

    @Select("select course_id from course_teacher where teacher_id = #{teacherId}")
    List<Integer> findCourseIdByTeacherId(Integer teacherId);

}
