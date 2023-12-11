package com.example.ooad_project_backend.entity;

import com.example.ooad_project_backend.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Data // 自动生成get、set方法
@EqualsAndHashCode(callSuper = false)// 自动生成equals和 hashcode方法
@Accessors(chain = true)    // 允许链式访问，例如：myObject.setField1("value1").setField2(42)。
public class GradeInfo implements Serializable {
    @Serial

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer releaser;

    private UserType releaserType;

    private Integer maxScore;

    private Integer grade;

    private Integer courseId;

    private Double proportion;

    private Integer studentId;

    private String content;

    private Integer fileId;

    private String gradeDescription;

    private Date submissionDate;

//    private boolean fresh;

}
