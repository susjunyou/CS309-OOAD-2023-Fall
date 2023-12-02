package com.example.ooad_project_backend.mapper;


import com.example.ooad_project_backend.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseTeacherMapper {

    @Select("select course_id from course_teacher where teacher_id = #{teacherId}")
    List<Integer> findCourseIdByTeacherId(Integer teacherId);

    @Select("select * from course_teacher left join teacher t on t.id = course_teacher.teacher_id where course_id = #{courseId}")
    List<TeacherInfo> findTeacherIdByCourseId(Integer courseId);

}
