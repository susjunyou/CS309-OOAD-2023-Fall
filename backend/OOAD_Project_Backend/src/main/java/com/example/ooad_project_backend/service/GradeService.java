package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.AttendanceInfo;
import com.example.ooad_project_backend.entity.GradeInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;


import java.util.List;

public interface GradeService extends IService<GradeInfo> {

    void freshAss(Integer studentId, Integer assignmentId);

    void freshPro(Integer studentId, Integer projectId);

    List<AssignmentInfo> findAssignmentGrade(Integer studentId, Integer AssignmentId);

    List<ProjectInfo> findProjectGrade(Integer studentId, Integer projectId);

    boolean addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade, String content, String grade_description);

    boolean addProjectGrade(Integer studentId, Integer projectId, Integer grade, String content, String grade_description);

    boolean updateAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade, String grade_description);

    List<AttendanceInfo> findAttendanceGradeByCourseIdAndStudentId(Integer courseId, Integer studentId);

    List<AssignmentInfo> getAssignmentSubmissionHistory(Integer studentId, Integer assignmentId);

    List<ProjectInfo> getProjectSubmissionHistory(Integer studentId, Integer projectId);

    boolean updateProjectGrade(Integer studentId, Integer projectId, Integer grade, String grade_description);

    boolean uploadAssignmentGradeCSV(Integer assignmentId, Integer studentId, Integer grade);

    boolean uploadProjectGradeCSV(Integer projectId, Integer studentId, Integer grade);

    boolean uploadAttendanceGradeCSV(Integer attendanceId, Integer studentId, Integer grade);

}
