package com.example.ooad_project_backend.controller;


import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.FileInfo;
import com.example.ooad_project_backend.enums.UserType;
import com.example.ooad_project_backend.service.FileService;
import com.example.ooad_project_backend.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

//@SuppressWarnings("ALL")
@RestController
@RequestMapping("/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private FileService fileService;

    @PostMapping("/addProject")
    public Result addProject(String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                             Integer maxScore, Double proportion, Integer releaser, UserType releaserType, Date projectStartDate, Integer maxPeopleInTeam, Integer courseId, MultipartFile file, Date teamDeadline) {

        FileInfo fileInfo = new FileInfo();
        try {
            if (file != null) {
                fileInfo.setFileName(file.getOriginalFilename());
                fileInfo.setFileType(file.getContentType());
                fileInfo.setFileData(file.getBytes());
                fileInfo = fileService.insertFile(fileInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        boolean flag = projectInfoService.addProject(projectTitle, projectDescription, projectDeadline, projectStatus,
                maxScore, proportion, releaser, releaserType, courseId,projectStartDate,maxPeopleInTeam,fileInfo.getId(), (Date) teamDeadline);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "添加失败");
        }

    }

    @GetMapping("/deleteProject")
    public Result deleteProject(Integer projectId) {
        boolean flag = projectInfoService.deleteProject(projectId);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "删除失败");
        }
    }

    @PostMapping("/updateProject")
    public Result updateProject(Integer projectId, String projectTitle, String projectDescription, Date projectDeadline, String projectStatus,
                                Integer maxScore, Double proportion, Integer releaser, UserType releaserType,Date projectStartDate,Integer maxPeopleInTeam, Integer courseId, MultipartFile file, Date TeamDeadline) {

        FileInfo fileInfo = new FileInfo();
        try {
            if (file != null) {
                fileInfo.setFileName(file.getOriginalFilename());
                fileInfo.setFileType(file.getContentType());
                fileInfo.setFileData(file.getBytes());
                fileInfo = fileService.insertFile(fileInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        boolean flag = projectInfoService.updateProject(projectId, projectTitle, projectDescription, projectDeadline, projectStatus,
                maxScore, proportion, releaser, releaserType, courseId,projectStartDate,maxPeopleInTeam,fileInfo.getId(),TeamDeadline);
        if (flag) {
            return Result.success();
        } else {
            return Result.error("1", "更新失败");
        }
    }

    @GetMapping("/getProjectByCourseId")
    public Result getProjectByCourseId(Integer courseId) {
        List<com.example.ooad_project_backend.entity.ProjectInfo> projectInfoList = projectInfoService.getProjectByCourseId(courseId);
        if (projectInfoList != null) {
            return Result.success(projectInfoList);
        } else {
            return Result.error("1", "获取失败");
        }
    }


}