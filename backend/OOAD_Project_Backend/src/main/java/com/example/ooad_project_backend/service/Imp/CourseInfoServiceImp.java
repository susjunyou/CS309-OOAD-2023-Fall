package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.*;
import com.example.ooad_project_backend.mapper.CourseDetailsMapper;
import com.example.ooad_project_backend.mapper.CourseInfoMapper;
import com.example.ooad_project_backend.mapper.CourseTeacherMapper;
import com.example.ooad_project_backend.mapper.ProjectInfoMapper;
import com.example.ooad_project_backend.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class CourseInfoServiceImp extends ServiceImpl<CourseInfoMapper, CourseInfo> implements CourseInfoService {

    @Autowired
    private CourseDetailsMapper courseDetailsMapper;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<ProjectInfo> findProjectInfoByCourseId(Integer courseId) {
        return projectInfoMapper.findProjectInfoByCourseId(courseId);
    }

    @Override
    public List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAssignmentInfoByCourseId(courseId);
    }

    @Override
    public List<PostInfo> findPostInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findPostInfoByCourseId(courseId);
    }

    @Override
    public List<AttendanceInfo> findAttendanceInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAttendanceInfoByCourseId(courseId);
    }

    @Override
    public List<MaterialInfo> findMaterialInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findMaterialInfoByCourseId(courseId);
    }

    @Override
    public void addStudent(Integer courseId, Integer studentId) {
        courseDetailsMapper.insertStudent(courseId, studentId);
    }

    @Override
    public void addSA(Integer courseId, Integer studentId) {
        courseDetailsMapper.insertSA(courseId, studentId);
    }

    @Override
    public void addTeacher(Integer courseId, Integer teacherId) {
        courseDetailsMapper.insertTeacher(courseId, teacherId);
    }

    @Override
    public List<Integer> findMySACourseIdByStudentId(Integer studentId) {
        return courseDetailsMapper.findMySACourseIdByStudentId(studentId);
    }

    @Override
    public List<Integer> findMySAIdByCourseId(Integer courseId) {
        return courseDetailsMapper.findMySAIdByCourseId(courseId);
    }

    @Override
    public List<StudentInfo> findAllStudentInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAllStudentInfoByCourseId(courseId);
    }

    @Override
    public List<StudentInfo> findAllSAInfoByCourseId(Integer courseId) {
        return courseDetailsMapper.findAllSAInfoByCourseId(courseId);
    }

    @Override
    public List<TeacherInfo> findAllTeacherInfoByCourseId(Integer courseId) {
        return courseTeacherMapper.findTeacherIdByCourseId(courseId);
    }

    @Override
    public void deleteStudent(Integer courseId, Integer studentId) {
        courseDetailsMapper.deleteStudent(courseId, studentId);
    }

    @Override
    public void deleteSA(Integer courseId, Integer studentId) {
        courseDetailsMapper.deleteSA(courseId, studentId);
    }

    @Override
    public void deleteTeacher(Integer courseId, Integer teacherId) {
        courseDetailsMapper.deleteTeacher(courseId, teacherId);
    }

    @Override
    public void deleteCourse(Integer courseId) {
        courseDetailsMapper.deleteCourse(courseId);
    }

    @Override
    public void releaseCourse(String courseCode, String courseName, String courseDescription) {
        courseDetailsMapper.releaseCourse(courseCode, courseName, courseDescription);
    }

    @Override
    public void updateCourse(Integer courseId, String courseCode, String courseName, String courseDescription) {
        courseDetailsMapper.updateCourse(courseId, courseCode, courseName, courseDescription);
    }

    @Override
    public List<CourseInfo> findAllCourseInfo() {
        return courseDetailsMapper.findAllCourseInfo();
    }

    @Override
    public List<StudentInfo> findStudentInfoNotInCourseByCourseId(Integer courseId) {
        return courseDetailsMapper.findStudentInfoNotInCourseByCourseId(courseId);
    }

    @Override
    public List<TeacherInfo> findTeacherInfoNotInCourseByCourseId(Integer courseId) {
        return courseDetailsMapper.findTeacherInfoNotInCourseByCourseId(courseId);
    }

    @Override
    public List<StudentInfo> findSAInfoNotInCourseByCourseId(Integer courseId) {
        List<StudentInfo> studentInfoList = courseDetailsMapper.findStudentInfoNotInCourseByCourseId(courseId);
        List<StudentInfo> SAInfoList = courseDetailsMapper.findAllSAInfoByCourseId(courseId);
        studentInfoList.removeIf(studentInfo -> SAInfoList.stream().anyMatch(saInfo -> saInfo.getId().equals(studentInfo.getId())));
        return studentInfoList;
    }

    @Override
    public List<CourseInfo> findMySACoursesByStudentId(Integer studentId) {
        List<Integer> courseIdList = courseDetailsMapper.findMySACourseIdByStudentId(studentId);
        return courseDetailsMapper.findCourseInfoByCourseIdList(courseIdList);
    }
}
