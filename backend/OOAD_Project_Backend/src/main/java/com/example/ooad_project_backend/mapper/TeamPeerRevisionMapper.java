package com.example.ooad_project_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ooad_project_backend.entity.TeamPeerRevisionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TeamPeerRevisionMapper extends BaseMapper<TeamPeerRevisionInfo> {


    @Select("select * from team_peer_revision where team1 = #{teamId} and project_id = #{projectId}")
    List<TeamPeerRevisionInfo> findOtherTeamGrade(Integer teamId, Integer projectId);

    @Insert("insert into team_peer_revision (team1, team2, project_id, grade1, comment) values (#{team1}, #{team2}, #{projectId}, #{grade1}, #{comment})")
    void insertTeamPeerRevision(TeamPeerRevisionInfo teamPeerRevisionInfo);

    @Update("update team_peer_revision set grade1 = #{grade1}, comment = #{comment} where team1 = #{teamId} and project_id = #{projectId}")
    void updateTeamPeerRevision(TeamPeerRevisionInfo teamPeerRevisionInfo);

    @Select("select * from team_peer_revision where team1 = #{team1} and team2 = #{team2} and project_id = #{projectId}")
    TeamPeerRevisionInfo findTeamRevisionByTeam1AndTeam2(Integer team1, Integer team2, Integer projectId);

}
