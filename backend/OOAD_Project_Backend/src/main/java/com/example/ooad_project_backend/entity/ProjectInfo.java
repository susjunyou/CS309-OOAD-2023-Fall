package com.example.ooad_project_backend.entity;


import com.example.ooad_project_backend.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Date;


@Data // 自动生成get、set方法
@EqualsAndHashCode(callSuper = false)// 自动生成equals和 hashcode方法
@Accessors(chain = true)    // 允许链式访问，例如：myObject.setField1("value1").setField2(42)。
public class ProjectInfo extends GradeInfo {

    private Integer projectSubmissionId;

    private String projectTitle;

    private String projectDescription;

    private Date projectStartDate;

    private Date projectDeadline;

    private StatusType projectStatus;

    private int maxPeopleInTeam;

    private Integer fileId;

    private Date TeamDeadline;

}
