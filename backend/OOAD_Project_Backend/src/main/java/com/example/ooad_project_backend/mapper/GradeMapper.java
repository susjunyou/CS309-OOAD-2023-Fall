package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.GradeInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GradeMapper extends BaseMapper<GradeInfo> {

    @Select("select * " +
            "from assignment " +
            "         right join assignment_submission on assignment.id = assignment_submission.assignment_id\n" +
            "where student_id = #{studentId}  and  assignment_id = #{assignmentId}")
    List<AssignmentInfo> findAssignmentGrade(Integer studentId, Integer assignmentId);

    @Select("select * " +
            "from project " +
            "         right join project_submission on project.id = project_submission.project_id\n" +
            "where student_id = #{studentId}  and  project_id = #{projectId}")
    List<ProjectInfo> findProjectGrade(Integer studentId, Integer projectId);

}
