package com.example.ooad_project_backend.mapper;

import com.example.ooad_project_backend.entity.*;
import org.apache.ibatis.annotations.Insert;
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
}


