package com.example.ooad_project_backend.controller;

import com.example.ooad_project_backend.common.Result;
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

    @PutMapping("/create")
    public Result create(TeamInfo teamInfo) {
        System.out.println(teamInfo.toString());
        return teamInfoService.createTeam(teamInfo) ? Result.success() : Result.error();
    }

    @PutMapping("/join")
    public Result join(Integer studentId, TeamInfo teamInfo) {
        System.out.println(teamInfo.toString());
        return teamInfoService.joinTeam(teamInfo, studentId) ? Result.success() : Result.error();
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


}
