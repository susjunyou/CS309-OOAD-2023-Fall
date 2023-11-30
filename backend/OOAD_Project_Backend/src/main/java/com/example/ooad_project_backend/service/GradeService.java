package com.example.ooad_project_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.GradeInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;

import java.sql.Date;
import java.util.List;

public interface GradeService extends IService<GradeInfo> {

    List<AssignmentInfo> findAssignmentGrade(Integer studentId, Integer AssignmentId);

    List<ProjectInfo> findProjectGrade(Integer studentId, Integer projectId);

    boolean addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade);

    boolean updateAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade);
}
