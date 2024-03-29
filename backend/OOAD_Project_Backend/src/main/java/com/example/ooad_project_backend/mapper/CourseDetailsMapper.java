package com.example.ooad_project_backend.mapper;

import com.example.ooad_project_backend.entity.*;
import com.example.ooad_project_backend.enums.StatusType;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Time;
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

    @Select("select * from student where id in (select student_id from course_student where course_id = #{courseId})")
    List<StudentInfo> findAllStudentInfoByCourseId(Integer courseId);

    @Select("select * from student where id in (select student_id from course_sa where course_id = #{courseId})")
    List<StudentInfo> findAllSAInfoByCourseId(Integer courseId);

    @Insert("insert into course_student (course_id, student_id) values (#{courseId}, #{studentId})")
    void insertStudent(Integer courseId, Integer studentId);

    @Insert("insert into course_sa (course_id, student_id) values (#{courseId}, #{studentId})")
    void insertSA(Integer courseId, Integer studentId);

    @Insert("insert into course_teacher (course_id, teacher_id) values (#{courseId}, #{teacherId})")
    void insertTeacher(Integer courseId, Integer teacherId);

    @Select("select course_id from course_sa where student_id = #{studentId}")
    List<Integer> findMySACourseIdByStudentId(Integer studentId);

    @Select("select course_sa_id from course_sa where course_id = #{courseId}")
    List<Integer> findMySAIdByCourseId(Integer courseId);

    @Delete("delete from course_student where course_id = #{courseId} and student_id = #{studentId}")
    void deleteStudent(Integer courseId, Integer studentId);

    @Delete("delete from course_sa where course_id = #{courseId} and student_id = #{studentId}")
    void deleteSA(Integer courseId, Integer studentId);

    @Delete("delete from course_teacher where course_id = #{courseId} and teacher_id = #{teacherId}")
    void deleteTeacher(Integer courseId, Integer teacherId);

    @Delete("delete from course where course_id = #{courseId}")
    void deleteCourse(Integer courseId);

    @Insert("insert into course (course_code, course_name, course_description) values ( #{courseCode}, #{courseName}, #{courseDescription})")
    void releaseCourse(String courseCode, String courseName, String courseDescription);

    @Update("update course set course_code = #{courseCode}, course_name = #{courseName}, course_description = #{courseDescription} where course_id = #{courseId}")
    void updateCourse(Integer courseId, String courseCode, String courseName, String courseDescription);

    @Select("select * from course")
    List<CourseInfo> findAllCourseInfo();

    @Select("select * " +
            "from student " +
            "         left join (select * from course_student where course_id = #{courseId}) cs on student.id = cs.student_id " +
            "where cs.student_id is null")
    List<StudentInfo> findStudentInfoNotInCourseByCourseId(Integer courseId);

    @Select("select * " +
            "from teacher " +
            "         left join (select * from course_teacher where course_id = #{courseId}) ct on teacher.id = ct.teacher_id " +
            "where ct.teacher_id is null")
    List<TeacherInfo> findTeacherInfoNotInCourseByCourseId(Integer courseId);

    @Delete("delete from course_teacher where course_id = #{courseId}")
    void deleteCourseTeacher(Integer courseId);

    @Delete("delete from course_student where course_id = #{courseId}")
    void deleteCourseStudent(Integer courseId);

    @Delete("delete from course_sa where course_id = #{courseId}")
    void deleteCourseSA(Integer courseId);

    @Insert("insert into attendance (course_id, attendance_date, attendance_start_time, attendance_deadline, attendance_status, max_score, proportion) values (#{courseId}, #{attendanceDate}, #{attendanceStartTime}, #{attendanceDeadline}, #{attendanceStatus}, #{maxScore}, #{proportion})")
    boolean addAttendance(Integer courseId, Date attendanceDate, Time attendanceStartTime, Time attendanceDeadline,
                       StatusType attendanceStatus, Integer maxScore, Integer proportion);


    @Update("update attendance set course_id = #{courseId}, attendance_date = #{attendanceDate}, attendance_start_time = #{attendanceStartTime}, attendance_deadline = #{attendanceDeadline}, attendance_status = #{attendanceStatus}, max_score = #{maxScore}, proportion = #{proportion} where id = #{id}")
    boolean updateAttendance(Integer id, Integer courseId,Date attendanceDate, Time attendanceStartTime, Time attendanceDeadline,
                          StatusType attendanceStatus, Integer maxScore, Integer proportion);
    @Delete("delete from attendance where id = #{id}")
    void deleteAttendance(Integer id);

}


