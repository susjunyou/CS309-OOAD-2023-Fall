package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.AttendanceInfo;
import com.example.ooad_project_backend.entity.GradeInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import org.apache.ibatis.annotations.*;

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

    @Insert("insert into assignment_submission (student_id, assignment_id, grade) values (#{studentId}, #{assignmentId}, #{grade})")
    boolean addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade);

    @Update("update assignment_submission set grade = #{grade} where student_id = #{studentId} and assignment_id = #{assignmentId}")
    boolean updateAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade);

    @Select("select * from attendance_submission where student_id = #{studentId}")
    @Results({
            @Result(property = "student_id", column = "student_id"),
            @Result(property = "attendance_id", column = "attendance_id"),
            @Result(property = "is_attended", column = "is_attended"),
            @Result(property = "attendance_submission_id", column = "attendance_submission_id")
    })
    List<AttendanceInfo> findAttendanceGradeByStudentId(Integer studentId);

    @Select("select * from attendance_submission where student_id = #{studentId} and course_id = #{courseId}")
    @Results({
            @Result(property = "student_id", column = "student_id"),
            @Result(property = "attendance_id", column = "attendance_id"),
            @Result(property = "is_attended", column = "is_attended"),
            @Result(property = "attendance_submission_id", column = "attendance_submission_id"),
            @Result(property = "course_id", column = "course_id")
    })
    List<AttendanceInfo> findAttendanceGradeByCourseIdAndStudentId(Integer courseId, Integer studentId);
}
