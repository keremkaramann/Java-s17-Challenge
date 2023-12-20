package com.workintech.s17friday.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    private Integer id;
    private String name;
    private int credit;
    Grade grade;
}
