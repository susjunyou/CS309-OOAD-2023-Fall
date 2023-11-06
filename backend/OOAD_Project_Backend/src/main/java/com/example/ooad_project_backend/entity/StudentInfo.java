package com.example.ooad_project_backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentInfo extends UserInfo{

    private int team_id;

    private int SID;



}
