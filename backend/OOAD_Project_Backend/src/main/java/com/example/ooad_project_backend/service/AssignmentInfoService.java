package com.example.ooad_project_backend.service;

import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.enums.UserType;

import java.sql.Date;
import java.util.List;

public interface AssignmentInfoService {
    List<AssignmentInfo> findAssignmentInfoByCourseId(Integer courseId);

    List<AssignmentInfo> findAssignmentInfoByStudentIdAndCourseId(Integer studentId, Integer courseId);

    boolean addAssignment(String assignmentTitle, String assignmentDescription, Date assignmentDeadline, String assignmentStatus,
                          Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId);

    boolean updateAssignment(Integer assignmentId, String assignmentTitle, String assignmentDescription, Date assignmentDeadline, String assignmentStatus,
                             Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Integer courseId);

    boolean deleteAssignment(Integer assignmentId);


}
