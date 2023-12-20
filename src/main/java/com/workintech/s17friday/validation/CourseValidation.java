package com.workintech.s17friday.validation;

import com.workintech.s17friday.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class CourseValidation {

    public static void checkName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ApiException("Name cannot be empty or null " + name, HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkId(Integer id) {
        if (id == null || id < 0) {
            throw new ApiException("Id cannot be empty or smaller than 0 " + id, HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkCredit(Integer credit) {
        if (credit == null || credit > 4 || credit < 0) {
            throw new ApiException("Credit must be between 0 and 4 also cannot be null " + credit, HttpStatus.BAD_REQUEST);
        }
    }
}
