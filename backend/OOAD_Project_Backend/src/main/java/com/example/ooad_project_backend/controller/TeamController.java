package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.common.Result;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeacherInfo;
import com.example.ooad_project_backend.entity.TeamInfo;
import com.example.ooad_project_backend.service.TeamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@CrossOrigin
public class TeamController {
    @Autowired
    private TeamInfoService teamInfoService;

    @GetMapping("/create")
    public Result create(TeamInfo teamInfo) {
        System.out.println(teamInfo.toString());
        return teamInfoService.createTeam(teamInfo) ? Result.success() : Result.error();
    }

    @PutMapping("/join")
    public Result join(Integer studentId, TeamInfo teamInfo) {
        System.out.println(teamInfo.toString());
        return teamInfoService.joinTeam(teamInfo, studentId) ? Result.success() : Result.error();
    }

    @DeleteMapping("/leave")
    public Result leave(Integer studentId, Integer teamId) {
        return teamInfoService.leaveTeam(teamId, studentId) ? Result.success() : Result.error();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer teamId) {
        return teamInfoService.deleteTeam(teamId) ? Result.success() : Result.error();
    }


    @GetMapping("/findTeamInfoByProjectId")
    public Result findTeamInfoByProjectId(Integer projectId) {
        List<TeamInfo> teamInfos = teamInfoService.findAllTeamInfoByProjectId(projectId);
        if (teamInfos.size() == 0) {
            return Result.error("1", "no teams");
        } else {
            return Result.success(teamInfos);
        }
    }

    @GetMapping("/findTeamMembers")
    public Result findTeamNumberByTeamId(Integer teamId) {
        List<StudentInfo> studentInfos = teamInfoService.findStudentInfoByTeamId(teamId);
        if (studentInfos.size() == 0) {
            return Result.error("1", "no team");
        } else {
            return Result.success(studentInfos);
        }
    }


}
