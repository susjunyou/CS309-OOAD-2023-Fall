package com.example.ooad_project_backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeamInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer teamId;

    private String teamName;

    private String teamDescription;

    private Integer leader; // leader's student_id

    private Integer projectId;

    private Integer teamSize;

    private List<Integer> teamMembers;

    private String recruitmentInformation;

    private Integer teacherId;

    private Date presentationDate;

    private boolean isPublic;


}
