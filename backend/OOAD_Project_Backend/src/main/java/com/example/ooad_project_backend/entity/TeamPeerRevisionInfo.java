package com.example.ooad_project_backend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeamPeerRevisionInfo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "team_peer_revision_id", type = IdType.AUTO)
    private Integer teamPeerRevisionId;

    private Integer team1;

    private Integer team2;

    private Integer grade;

    private String comment;

    private Integer projectId;
}
