package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.*;
import com.example.ooad_project_backend.enums.StatusType;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public interface CourseInfoService extends IService<CourseInfo> {
    List<ProjectInfo> findProjectInfoByCourseId(Integer courseId);

    List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId);

    List<PostInfo> findPostInfoByCourseId(Integer courseId);

    List<AttendanceInfo> findAttendanceInfoByCourseId(Integer courseId);

    List<MaterialInfo> findMaterialInfoByCourseId(Integer courseId);

    void addStudent(Integer courseId, Integer studentId);

    void addSA(Integer courseId, Integer studentId);

    void addTeacher(Integer courseId, Integer teacherId);

    List<Integer> findMySACourseIdByStudentId(Integer studentId);

    List<Integer> findMySAIdByCourseId(Integer courseId);

    List<StudentInfo> findAllStudentInfoByCourseId(Integer courseId);

    List<StudentInfo> findAllSAInfoByCourseId(Integer courseId);

    List<TeacherInfo> findAllTeacherInfoByCourseId(Integer courseId);

    void deleteStudent(Integer courseId, Integer studentId);

    void deleteSA(Integer courseId, Integer studentId);

    void deleteTeacher(Integer courseId, Integer teacherId);

    void deleteCourse(Integer courseId);

    void releaseCourse(String courseCode, String courseName, String courseDescription);

    void updateCourse(Integer courseId, String courseCode, String courseName, String courseDescription);

    List<CourseInfo> findAllCourseInfo();

    List<StudentInfo> findStudentInfoNotInCourseByCourseId(Integer courseId);

    List<TeacherInfo> findTeacherInfoNotInCourseByCourseId(Integer courseId);

    List<StudentInfo> findSAInfoNotInCourseByCourseId(Integer courseId);

    List<CourseInfo> findMySACoursesByStudentId(Integer studentId);


    boolean addAttendance(Integer courseId, Date AttendanceDate, Time AttendanceStartTime, Time AttendanceDeadline,
                          StatusType AttendanceStatus, Integer max_score, Integer proportion);

    boolean updateAttendance(Integer courseId, Date AttendanceDate, Time AttendanceStartTime, Time AttendanceDeadline,
                            StatusType AttendanceStatus, Integer max_score, Integer proportion);

    void deleteAttendance(Integer id);

}
