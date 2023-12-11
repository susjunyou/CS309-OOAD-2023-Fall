package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.AssignmentInfo;
import com.example.ooad_project_backend.entity.UserInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.AssignmentInfoService;
import com.example.ooad_project_backend.service.ProjectInfoService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

//@SuppressWarnings("ALL")
@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectInfoService projectInfoService;

    @PostMapping("/addProject")
    public Result addProject(String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                             Integer maxScore, Double proportion, String releaser, UserType releaserType,Date projectStartDate,Integer maxPeopleInTeam, Integer courseId) {
        boolean flag = projectInfoService.addProject(projectTitle, projectDescription, projectDeadline, projectStatus,
                maxScore, proportion, releaser, releaserType, courseId,projectStartDate,maxPeopleInTeam);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "添加失败");
        }
    }

    @DeleteMapping("/deleteProject")
    public Result deleteProject(Integer projectId) {
        boolean flag = projectInfoService.deleteProject(projectId);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "删除失败");
        }
    }

    @GetMapping("/updateProject")
    public Result updateProject(Integer projectId, String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                                Integer maxScore, Double proportion, String releaser, UserType releaserType,Date projectStartDate,Integer maxPeopleInTeam, Integer courseId) {
        boolean flag = projectInfoService.updateProject(projectId, projectTitle, projectDescription, projectDeadline, projectStatus,
                maxScore, proportion, releaser, releaserType, courseId,projectStartDate,maxPeopleInTeam);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "更新失败");
        }
    }

    @PostMapping("/getProjectByCourseId")
    public Result getProjectByCourseId(Integer courseId) {
        List<com.example.ooad_project_backend.entity.ProjectInfo> projectInfoList = projectInfoService.getProjectByCourseId(courseId);
        if (projectInfoList != null) {
            return Result.success(projectInfoList);
        } else {
            return Result.error("1", "获取失败");
        }
    }


}
