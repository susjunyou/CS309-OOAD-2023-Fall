package com.example.ooad_project_backend.mapper;

import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.AttendanceInfo;
import com.example.ooad_project_backend.entity.MaterialInfo;
import com.example.ooad_project_backend.entity.PostInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseDetailsMapper {

    @Select("select * from assignment where course_id = #{courseId}")
    List<AssignmentInfo> findAssignmentInfoByCourseId(Integer CourseId);

    @Select("select * from post where course_id = #{courseId}")
    List<PostInfo> findPostInfoByCourseId(Integer CourseId);

    @Select("select * from attendance where course_id = #{courseId}")
    List<AttendanceInfo> findAttendanceInfoByCourseId(Integer CourseId);

    @Select("select * from material where course_id = #{courseId}")
    List<MaterialInfo> findMaterialInfoByCourseId(Integer CourseId);

}
