package com.example.ooad_project_backend.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ooad_project_backend.entity.JoinTeamInfo;
import com.example.ooad_project_backend.entity.StudentInfo;
import com.example.ooad_project_backend.entity.TeamInfo;
import com.example.ooad_project_backend.entity.TeamPeerRevisionInfo;
import com.example.ooad_project_backend.mapper.*;
import com.example.ooad_project_backend.service.TeamInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamInfoServiceImp extends ServiceImpl<TeamMapper, TeamInfo> implements TeamInfoService {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private CourseDetailsMapper courseDetailsMapper;

    @Autowired
    private TeamPeerRevisionMapper teamPeerRevisionMapper;

    @Override
    public boolean updateTeamInfo(TeamInfo teamInfo) {
        teamMapper.updateTeamInfo(teamInfo);
        return true;
    }

    @Override
    public boolean updateTeamLeader(Integer teamId, Integer studentId) {
        teamMapper.updateTeamLeader(teamId, studentId);
        return true;
    }

    @Override
    public boolean createTeam(TeamInfo teamInfo) {
        if (teamInfo.getLeader() == null) {
            teamMapper.createTeam(teamInfo);
            return true;
        }
        Integer maxTeamSize = projectInfoMapper.findMaxPeopleInTeamById(teamInfo.getProjectId());
        // 判断队伍人数是否超过最大人数
        if (maxTeamSize < teamInfo.getTeamSize()) {
            return false;
        }
        // 检查创建队伍的人是否位于其他的队伍
        Integer teamId = teamMapper.findTeamIdByProjectIdAndStudentId(teamInfo.getProjectId(), teamInfo.getLeader());
        if (teamId != null) {
            return false;
        }
        if (teamInfo.getTeamSize() == null || teamInfo.getProjectId() == null || teamInfo.getTeamName() == null) {
            return false;
        }
        teamMapper.createTeam(teamInfo);
        TeamInfo team = teamMapper.findTeamInfoByProjectIdAndLeader(teamInfo.getProjectId(), teamInfo.getLeader());
        teamMapper.joinTeam(team.getTeamId(), teamInfo.getLeader(), teamInfo.getProjectId());
        return true;
    }

    @Override
    public boolean joinTeam(TeamInfo teamInfo, Integer studentId) {
        // 判断队伍是否为空
        if (teamMapper.findTeamInfoByTeamId(teamInfo.getTeamId()).getLeader() == null) {
            teamMapper.setLeader(teamInfo.getTeamId(), studentId);
            teamMapper.joinTeam(teamInfo.getTeamId(), studentId, teamInfo.getProjectId());
            return true;
        }
        // 判断是否加入该项目的其他队伍
        if (teamMapper.findTeamIdByProjectIdAndStudentId(teamInfo.getProjectId(), studentId) != null) {
            return false;
        }
        // 判断这个team是否已经满员
        if (teamMapper.findStudentIdsByTeamIdAndProjectId(teamInfo.getTeamId(), teamInfo.getProjectId()).size() >= teamInfo.getTeamSize()) {
            return false;
        }
        try {
            teamMapper.joinTeam(teamInfo.getTeamId(), studentId, teamInfo.getProjectId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<TeamInfo> findAllTeamInfoByProjectId(Integer projectId) {
        return teamMapper.findTeamInfoByProjectId(projectId);
    }

    @Override
    public List<StudentInfo> findStudentInfoByTeamId(Integer teamId) {
        List<Integer> studentIds = teamMapper.findStudentIdsByTeamId(teamId);
        List<StudentInfo> studentInfos = new ArrayList<>();
        for (Integer studentId : studentIds) {
            studentInfos.add(studentInfoMapper.findStudentInfoById(studentId));
        }
        return studentInfos;
    }

    @Override
    public TeamInfo findTeamInfoByTeamId(Integer teamId) {
        return teamMapper.findTeamInfoByTeamId(teamId);
    }

    @Override
    public boolean leaveTeam(Integer teamId, Integer studentId) {
        teamMapper.leaveTeam(teamId, studentId);
        List<Integer> studentIds = teamMapper.findStudentIdsByTeamId(teamId);
        if (studentIds.size() == 0) {
            deleteTeam(teamId);
        }
        return true;
    }

    @Override
    public boolean deleteTeam(Integer teamId) {
        teamMapper.deleteTeamInTeamStudent(teamId);
        teamMapper.deleteTeamInTeam(teamId);
        return true;
    }

    @Override
    public List<TeamInfo> findAllTeamInfoByStudentId(Integer studentId) {
        return teamMapper.findTeamInfoByStudentId(studentId);
    }

    @Override
    public boolean requestJoinTeam(Integer teamId, Integer studentId, Integer projectId) {
        if (teamMapper.findTeamInfoByTeamId(teamId).getLeader() == null) {
            teamMapper.setLeader(teamId, studentId);
            teamMapper.joinTeam(teamId, studentId, projectId);
            return true;
        }
        List<Integer> teamIds = teamMapper.findTeamIdByStudentIdInRequest(studentId);
        for (Integer teamId1 : teamIds) {
            if (teamId1.equals(teamId)) {
                return false;
            }
        }
        teamMapper.requestJoinTeam(teamId, studentId, projectId);
        return true;
    }

    @Override
    public List<JoinTeamInfo> getRequestsJoinTeam(Integer teamId) {
        //        List<StudentInfo> studentInfos = new ArrayList<>();
//        for (JoinTeamInfo joinTeamInfo : joinTeamInfos) {
//            studentInfos.add(studentInfoMapper.findStudentInfoById(joinTeamInfo.getStudentId()));
//        }
        return teamMapper.getRequestsStudentIdByTeamId(teamId);
    }

    @Override
    public List<JoinTeamInfo> getInvitesJoinTeam(Integer studentId) {
        //        List<TeamInfo> teamInfos = new ArrayList<>();
//        for (Integer teamId : teamIds) {
//            teamInfos.add(teamMapper.findTeamInfoByTeamId(teamId));
//        }
        return teamMapper.getInvitesJoinTeam(studentId);
    }

    @Override
    public boolean manageTeamRequest(Integer requestId, boolean isAccepted) {
        if (isAccepted) {
            Integer studentId = teamMapper.findStudentIdByRequestId(requestId);
            Integer teamId = teamMapper.findTeamIdByRequestId(requestId);
            teamMapper.joinTeam(teamId, studentId, teamMapper.findProjectIdByTeamId(teamId));
            teamMapper.deleteRequest(requestId);
            //同时删掉邀请
            teamMapper.deleteInviteByStudentId(studentId);
            teamMapper.deleteRequestByStudentId(studentId);
        } else {
            teamMapper.deleteRequest(requestId);
        }
        return true;
    }

    @Override
    public boolean manageInvite(Integer id, boolean isAccepted) {
        if (isAccepted) {
            Integer studentId = teamMapper.findStudentIdByInviteId(id);
            Integer teamId = teamMapper.findTeamIdByInviteId(id);
            teamMapper.joinTeam(teamId, studentId, teamMapper.findProjectIdByTeamId(teamId));
            teamMapper.deleteInvite(id);
            //同时删掉申请
            teamMapper.deleteInviteByStudentId(studentId);
            teamMapper.deleteRequestByStudentId(studentId);
        } else {
            teamMapper.deleteInvite(id);
        }
        return true;
    }

    @Override
    public boolean inviteStudent(Integer teamId, Integer studentId, Integer projectId) {
        TeamInfo teamInfo = teamMapper.findTeamInfoByTeamId(teamId);
        List<Integer> teamIds = teamMapper.findTeamIdByStudentIdInInvite(studentId);
        for (Integer teamId1 : teamIds) {
            if (teamId1.equals(teamId)) {
                return false;
            }
        }
        // 判断是否加入该项目的其他队伍
        if (teamMapper.findTeamIdByProjectIdAndStudentId(teamInfo.getProjectId(), studentId) != null) {
            return false;
        }
        // 判断这个team是否已经满员
        if (teamMapper.findStudentIdsByTeamIdAndProjectId(teamInfo.getTeamId(), teamInfo.getProjectId()).size() >= teamInfo.getTeamSize()) {
            return false;
        }
        for (JoinTeamInfo joinTeamInfo : teamMapper.getInvitesJoinTeam(studentId)) {
            if (joinTeamInfo.getTeamId().equals(teamId)) {
                return false;
            }
        }
        try {
            teamMapper.addInviteJoinTeam(teamId, studentId, projectId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updatePresentationDateByTeamId(Integer teamId, Date presentationDate, Integer teacherId) {
        teamMapper.updatePresentationDateByTeamId(teamId, presentationDate, teacherId);
        return true;
    }

    @Override
    public List<StudentInfo> findStudentNotJoinTeam(Integer projectId, Integer courseId) {
        List<Integer> alreadyJoinStudentIds = teamMapper.findAlreadyJoinedTeamStudentIdsByProjectId(projectId);
        List<StudentInfo> allStudentIds = courseDetailsMapper.findAllStudentInfoByCourseId(courseId);
        List<StudentInfo> studentInfos = new ArrayList<>();
        for (StudentInfo studentInfo : allStudentIds) {
            if (!alreadyJoinStudentIds.contains(studentInfo.getId())) {
                studentInfos.add(studentInfo);
            }
        }
        return studentInfos;
    }

    @Override
    public boolean addPresentation(Integer teamId, Date presentationDate, Integer teacherId) {
        teamMapper.addPresentation(teamId, presentationDate, teacherId);
        return true;
    }

    @Override
    public boolean deletePresentation(Integer teamId) {
        teamMapper.deletePresentation(teamId);
        return true;
    }

    @Override
    public List<TeamPeerRevisionInfo> findTeamPeerRevision(Integer teamId, Integer projectId) {
        return teamPeerRevisionMapper.findOtherTeamGrade(teamId, projectId);
    }

    @Override
    public boolean updateTeamPeerRevision(TeamPeerRevisionInfo teamPeerRevisionInfo) {
        TeamPeerRevisionInfo teamPeerRevisionInfo1 = teamPeerRevisionMapper.findTeamRevisionByTeam1AndTeam2(teamPeerRevisionInfo.getTeam1(), teamPeerRevisionInfo.getTeam2(), teamPeerRevisionInfo.getProjectId());
        if (teamPeerRevisionInfo1 != null) {
            teamPeerRevisionMapper.updateTeamPeerRevision(teamPeerRevisionInfo);
            return true;
        } else {
            teamPeerRevisionMapper.insertTeamPeerRevision(teamPeerRevisionInfo);
            return true;
        }
    }

    @Override
    public List<TeamInfo> findAlreadyPeerGradeTeam(Integer teamId, Integer projectId) {
        List<Integer> teamIds = teamPeerRevisionMapper.findAlreadyPeerGradeTeam(teamId, projectId);
        List<TeamInfo> teamInfos = new ArrayList<>();
        for (Integer teamId1 : teamIds) {
            teamInfos.add(teamMapper.findTeamInfoByTeamId(teamId1));
        }
        return teamInfos;
    }

    @Override
    public List<TeamInfo> findNotYetPeerGradeTeam(Integer teamId, Integer projectId) {
        List<Integer> teamIds = teamPeerRevisionMapper.findAlreadyPeerGradeTeam(teamId, projectId);
        List<TeamInfo> teamInfos = teamMapper.findTeamInfoByProjectId(projectId);
        teamInfos.removeIf(teamInfo -> teamIds.contains(teamInfo.getTeamId()));
        teamInfos.removeIf(teamInfo -> teamInfo.getTeamId().equals(teamId));
        return teamInfos;
    }

    @Override
    public boolean modifyIsPublic(Integer teamId, boolean privacyTeam) {
        teamMapper.modifyIsPublic(teamId, privacyTeam);
        return true;
    }


}
