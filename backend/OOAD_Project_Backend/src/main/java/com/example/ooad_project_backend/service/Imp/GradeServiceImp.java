package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.AttendanceInfo;
import com.example.ooad_project_backend.entity.GradeInfo;
import com.example.ooad_project_backend.entity.ProjectInfo;
import com.example.ooad_project_backend.mapper.AssignmentMapper;
import com.example.ooad_project_backend.mapper.GradeMapper;
import com.example.ooad_project_backend.mapper.ProjectInfoMapper;
import com.example.ooad_project_backend.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImp extends ServiceImpl<GradeMapper, GradeInfo> implements GradeService {


    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

//    @Override
//    public void freshAss(Integer studentId, Integer assignmentId) {
//        gradeMapper.freshAss(studentId, assignmentId);
//    }
//
//    @Override
//    public void freshPro(Integer studentId, Integer projectId) {
//        gradeMapper.freshPro(studentId, projectId);
//    }


    @Override
    public AssignmentInfo findAssignmentGrade(Integer studentId, Integer AssignmentId) {
        List<AssignmentInfo> assignmentInfos = gradeMapper.findAssignmentGrade(studentId, AssignmentId);
        AssignmentInfo assignmentInfos1;
        if (assignmentInfos.size() == 0) {
            return null;
        } else {
            assignmentInfos1 = assignmentInfos.get(0);
            if (assignmentInfos1.getGrade() == null) return null;
        }
        for (AssignmentInfo assignmentInfo : assignmentInfos) {
            if (assignmentInfo.getGrade() != null && assignmentInfo.getGrade() > assignmentInfos1.getGrade() ) {
                assignmentInfos1 = assignmentInfo;
            }
        }
        return assignmentInfos1;

    }

    @Override
    public ProjectInfo findProjectGrade(Integer studentId, Integer projectId) {
        List<ProjectInfo> projectInfos = gradeMapper.findProjectGrade(studentId, projectId);
        ProjectInfo projectInfos1;
        if (projectInfos.size() == 0) {
            return null;
        } else {
            projectInfos1 = projectInfos.get(0);
            if (projectInfos1.getGrade() == null) return null;
        }
        for (ProjectInfo projectInfo : projectInfos) {
            if (projectInfo.getGrade() != null && projectInfo.getGrade() > projectInfos1.getGrade()) {
                projectInfos1 = projectInfo;
            }
        }
        return projectInfos1;
    }

    @Override
    public boolean addAssignmentGrade(Integer studentId, Integer assignmentId, Integer grade, String content, String grade_description) {
        return gradeMapper.addAssignmentGrade(studentId, assignmentId, grade, content, grade_description);
    }

    @Override
    public boolean addProjectGrade(Integer studentId, Integer projectId, Integer grade, String content, String grade_description) {
        return gradeMapper.addProjectGrade(studentId, projectId, grade, content, grade_description);
    }

    @Override
    public boolean updateAssignmentGrade(Integer studentId, Integer assignmentSubmissionId, Integer grade, String grade_description) {
        return gradeMapper.updateAssignmentGrade(studentId, assignmentSubmissionId, grade, grade_description);
    }

    @Override
    public boolean updateProjectGrade(Integer studentId, Integer projectSubmissionId, Integer grade, String grade_description) {
        return gradeMapper.updateProjectGrade(studentId, projectSubmissionId, grade, grade_description);
    }


    @Override
    public List<AttendanceInfo> findAttendanceGradeByCourseIdAndStudentId(Integer courseId, Integer studentId) {
        return gradeMapper.findAttendanceGradeByCourseIdAndStudentId(courseId, studentId);
    }

    @Override
    public List<AssignmentInfo> getAssignmentSubmissionHistory(Integer studentId, Integer assignmentId) {
        return gradeMapper.getAssignmentSubmissionHistory(studentId, assignmentId);
    }

    @Override
    public List<ProjectInfo> getProjectSubmissionHistory(Integer studentId, Integer projectId) {
        return gradeMapper.getProjectSubmissionHistory(studentId, projectId);
    }


    @Override
    public boolean uploadAssignmentGradeCSV(Integer assignmentId, Integer studentId, Integer grade) {
        AssignmentInfo assignmentInfo = assignmentMapper.findAssignmentInfoByAssignmentId(assignmentId);
        if (assignmentInfo == null) return false;
        gradeMapper.addAssignmentGrade(studentId, assignmentId, grade, "null", "null");
        return true;
    }

    @Override
    public boolean uploadProjectGradeCSV(Integer projectId, Integer studentId, Integer grade) {
        ProjectInfo projectInfo = projectInfoMapper.findProjectInfoByProjectId(projectId);
        if (projectInfo == null) return false;
        gradeMapper.addProjectGrade(studentId, projectId, grade, "null", "null");
        return true;
    }

    @Override
    public boolean uploadAttendanceGradeCSV(Integer attendanceId, Integer studentId, Integer grade) {
        AttendanceInfo attendanceInfo = gradeMapper.findAttendanceInfoByAttendanceId(attendanceId);
        if (attendanceInfo == null) return false;
        gradeMapper.addAttendanceGrade(studentId, attendanceId, grade > 0);
        return true;
    }

    @Override
    public AttendanceInfo findAttendanceGrade(Integer studentId, Integer attendanceId) {
        List<AttendanceInfo> attendanceInfos = gradeMapper.findAttendanceGrade(studentId, attendanceId);
        for (AttendanceInfo attendanceInfo : attendanceInfos) {
            if (attendanceInfo.getGrade() != null) {
                return attendanceInfo;
            }
        }
        if (attendanceInfos.size() == 0) return null;
        return attendanceInfos.get(0);
    }

}
