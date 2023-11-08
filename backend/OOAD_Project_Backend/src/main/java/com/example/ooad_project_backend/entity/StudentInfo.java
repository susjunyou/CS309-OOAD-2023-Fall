package com.example.ooad_project_backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentInfo extends UserInfo{

    private int SID;

    private boolean isSA;

    private List<Long> SA_courses;


}
