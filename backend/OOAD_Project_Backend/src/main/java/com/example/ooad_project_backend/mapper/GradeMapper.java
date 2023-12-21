package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.AttendanceInfo;
import com.example.ooad_project_backend.entity.GradeInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GradeMapper extends BaseMapper<GradeInfo> {

//    @Update("update assignment_submission set fresh = false where student_id = #{studentId} and assignment_id = #{assignmentId}")
//    void freshAss(Integer studentId, Integer assignmentId);
//
//    @Update("update project_submission set fresh = false where student_id = #{studentId} and project_id = #{projectId}")
//    void freshPro(Integer studentId, Integer projectId);

    @Select("select * " +
            "from assignment " +
            "         right join assignment_submission on assignment.id = assignment_submission.assignment_id\n" +
            "where student_id = #{studentId}  and  assignment_id = #{assignmentId} and grade is not null")
    List<AssignmentInfo> findAssignmentGrade(Integer studentId, Integer assignmentId);

    @Select("select * " +
            "from project " +
            "         right join project_submission on project.id = project_submission.project_id\n" +
            "where student_id = #{studentId}  and  project_id = #{projectId} and grade is not null")
    List<ProjectInfo> findProjectGrade(Integer studentId, Integer projectId);

    @Insert("insert into assignment_submission (student_id, assignment_id, grade, content, grade_description) values (#{studentId}, #{assignmentId}, #{grade}, #{content}, #{grade_description})")
    boolean addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade, String content, String grade_description);


    @Insert("insert into project_submission (student_id, project_id, grade,content, grade_description) values (#{studentId}, #{projectId}, #{grade}, #{content}, #{grade_description})")
    boolean addProjectGrade(Integer studentId, Integer projectId, Integer grade, String content, String grade_description);

    //更改grade, content和grade_description

    @Update("update assignment_submission set grade = #{grade}, grade_description =#{gradeDescription}  where student_id = #{studentId} and assignment_submission_id = #{assignmentSubmissionId}")
    boolean updateAssignmentGrade(Integer studentId, Integer assignmentSubmissionId, Integer grade, String gradeDescription);

    @Update("update project_submission set grade = #{grade}, grade_description =#{gradeDescription}  where student_id = #{studentId} and project_submission_id = #{projectSubmissionId}")
    boolean updateProjectGrade(Integer studentId, Integer projectSubmissionId, Integer grade, String gradeDescription);




    @Select("select * " +
            "from assignment_submission " +
            "         left join assignment a on a.id = assignment_submission.assignment_id " +
            "where student_id = #{studentId}" +
            "  and assignment_id = #{assignmentId}")
    List<AssignmentInfo> getAssignmentSubmissionHistory(Integer studentId, Integer assignmentId);

    @Select("select * " +
            "from project_submission " +
            "         left join project p on p.id = project_submission.project_id " +
            "where student_id = #{studentId}" +
            "  and project_id = #{projectId}")
    List<ProjectInfo> getProjectSubmissionHistory(Integer studentId, Integer projectId);

    @Select("select * from attendance_submission left join attendance a on a.id = attendance_submission.attendance_id where student_id = #{studentId} and a.course_id = #{courseId}")
    List<AttendanceInfo> findAttendanceGradeByCourseIdAndStudentId(Integer courseId, Integer studentId);

    @Select("select * from attendance where id = #{attendanceId}")
    AttendanceInfo findAttendanceInfoByAttendanceId(Integer attendanceId);

    @Insert("insert into attendance_submission (attendance_id, student_id, is_attended) values (#{attendanceId}, #{studentId}, #{isPresent})")
    void addAttendanceGrade(Integer attendanceId, Integer studentId, Boolean isPresent);

    @Select("select * " +
            "from attendance " +
            "         right join attendance_submission a on attendance.id = a.attendance_id " +
            "where attendance.id = #{attendanceId} " +
            "  and a.student_id = #{studentId}")
    List<AttendanceInfo> findAttendanceGrade(Integer studentId, Integer attendanceId);

}
