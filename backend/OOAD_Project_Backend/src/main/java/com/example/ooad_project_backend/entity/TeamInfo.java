package com.example.ooad_project_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeamInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "team_id", type = IdType.AUTO)
    private Integer teamId;

    @TableId(value = "team_name", type = IdType.AUTO)
    private String teamName;

    @TableId(value = "team_description", type = IdType.AUTO)
    private String teamDescription;

    @TableId(value = "leader", type = IdType.AUTO)
    private Integer leader; // leader's student_id

    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    @TableId(value = "team_size", type = IdType.AUTO)
    private Integer teamSize;

    @TableId(value = "team_members", type = IdType.AUTO)
    private List<Integer> teamMembers;


}
