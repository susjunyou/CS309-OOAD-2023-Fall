package com.example.ooad_project_backend.entity;

import com.example.ooad_project_backend.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Date;

@Data // 自动生成get、set方法
@EqualsAndHashCode(callSuper = false)// 自动生成equals和 hashcode方法
@Accessors(chain = true)    // 允许链式访问，例如：myObject.setField1("value1").setField2(42)。
public class AssignmentInfo extends GradeInfo {

    private  Integer assignmentSubmissionId;

    private String assignmentTitle;

    private String assignmentDescription;

    private Date assignmentDeadline;

    private StatusType assignmentStatus;

}
