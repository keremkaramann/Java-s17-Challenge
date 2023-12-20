package com.workintech.s17friday.model;

import org.springframework.stereotype.Component;

@Component
public class HighCourseGpa implements CourseGpa {
    @Override
    public int getGpa() {
        return 10;
    }
}
