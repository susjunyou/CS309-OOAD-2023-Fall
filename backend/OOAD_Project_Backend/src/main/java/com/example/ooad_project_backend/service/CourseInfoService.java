package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.*;

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
}
