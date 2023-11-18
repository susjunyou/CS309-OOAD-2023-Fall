package com.example.ooad_project_backend.entity;

import com.example.ooad_project_backend.enums.DepartmentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentInfo extends UserInfo {

    private boolean isSA;

    private String major;

    private Integer level;

    private DepartmentType department;

    private List<Long> SA_courses;

}
