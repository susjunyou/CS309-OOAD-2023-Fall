package com.example.ooad_project_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class teamInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "team_id", type = IdType.AUTO)
    private Integer team_id;

    private String teamName;

    private String teamDescription;

    private UserInfo leader;

    private UserInfo teacher;

    private Integer teamSize;


}
