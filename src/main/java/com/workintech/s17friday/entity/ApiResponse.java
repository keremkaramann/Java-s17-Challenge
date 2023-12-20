package com.workintech.s17friday.entity;

import com.workintech.s17friday.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private Course course;
    private Integer totalGpa;
}
