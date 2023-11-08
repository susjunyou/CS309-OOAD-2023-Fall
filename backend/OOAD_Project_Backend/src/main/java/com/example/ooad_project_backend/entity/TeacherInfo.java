package com.example.ooad_project_backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data // 自动生成get、set方法
@EqualsAndHashCode(callSuper = false)// 自动生成equals和 hashcode方法
@Accessors(chain = true)    // 允许链式访问，例如：myObject.setField1("value1").setField2(42)。
public class TeacherInfo extends UserInfo {

    //    @Column(name = "zhicheng")
    private String tenure;
    //    @Column(name = "zhuanyeId")
    private Long majorId;
    //    @Transient
    private String majorName;

    private String department;

    private List<Long> courseId;


}
