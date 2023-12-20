package com.workintech.s17friday.controller;

import com.workintech.s17friday.entity.ApiResponse;
import com.workintech.s17friday.exceptions.ApiException;
import com.workintech.s17friday.model.Course;
import com.workintech.s17friday.model.HighCourseGpa;
import com.workintech.s17friday.model.LowCourseGpa;
import com.workintech.s17friday.model.MediumCourseGpa;
import com.workintech.s17friday.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/courses")
public class CourseController {
    List<Course> courses;
    LowCourseGpa lowCourseGpa;
    MediumCourseGpa mediumCourseGpa;
    HighCourseGpa highCourseGpa;

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") LowCourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") MediumCourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public void init() {
        this.courses = new ArrayList<>();
    }

    @GetMapping
    public List<Course> get() {
        return this.courses;
    }

    @GetMapping("/{name}")
    public Course getByName(@PathVariable("name") String name) {
        return courses.stream()
                .filter(course -> course.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ApiException(name + " Named course is not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> create(@RequestBody Course course) {
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkName(course.getName());
        courses.add(course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse api = new ApiResponse(course, totalGpa);
        return new ResponseEntity<>(api, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Integer id, @RequestBody Course course) {
        CourseValidation.checkId(id);
        CourseValidation.checkName(course.getName());
        CourseValidation.checkCredit(course.getCredit());

        Course existing = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiException("Course does not exist ", HttpStatus.BAD_REQUEST));

        int indexOfExistingCourse = courses.indexOf(existing);
        courses.set(indexOfExistingCourse, course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(courses.get(indexOfExistingCourse), totalGpa);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        Course exist = courses.stream()
                .filter(course -> course.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiException("course not found with this id: ", HttpStatus.BAD_REQUEST));
    }

    private Integer getTotalGpa(Course course) {
        if (course.getCredit() <= 2) {
            return course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
        } else if (course.getCredit() == 3) {
            return course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
        } else {
            return course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
        }
    }

}
