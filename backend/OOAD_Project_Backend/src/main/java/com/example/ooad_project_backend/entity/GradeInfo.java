package com.example.ooad_project_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;

public class GradeInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "grade_id", type = IdType.AUTO)
    private Integer gradeId;

    private UserInfo releaser;
    private Integer maxScore;

    private Integer grade;

    private CourseInfo course;

    private Integer proportion;

    private GradeBook gradeBook;

}
