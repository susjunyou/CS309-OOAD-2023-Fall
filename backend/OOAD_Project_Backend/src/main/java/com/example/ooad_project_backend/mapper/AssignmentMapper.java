package com.example.ooad_project_backend.mapper;


import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssignmentMapper {

        @Select("select * from assignment where course_id = #{courseId}")
        List<AssignmentInfo> findAllAssignmentInfoByCourseId(Integer courseId);
}
