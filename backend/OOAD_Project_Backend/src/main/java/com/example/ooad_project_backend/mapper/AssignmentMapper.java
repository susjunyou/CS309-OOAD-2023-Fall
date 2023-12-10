package com.example.ooad_project_backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssignmentMapper extends BaseMapper<AssignmentInfo> {

        @Select("select * from assignment where course_id = #{courseId}")
        List<AssignmentInfo> findAllAssignmentInfoByCourseId(Integer courseId);

        @Select("select * from assignment where id = #{assignmentId}")
        AssignmentInfo findAssignmentInfoByAssignmentId(Integer assignmentId);

       @Select("select * from assignment_submission where student_id = #{studentId} and assignment_id = #{courseId}")
        List<AssignmentInfo> findAssignmentInfoByStudentIdAndCourseId(Integer studentId, Integer courseId);
}
