package com.sprk.one_to_many.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"courses"})
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructorId;

    private String firstName;

    private String lastName;

    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_detail_id")
    @JsonManagedReference
    private InstructorDetail instructorDetail;

    //    Many Courses
    @OneToMany(mappedBy = "instructor",fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonManagedReference
//    @JsonIgnore
    private List<Course> courses;


    void addCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<Course>();
        }
        courses.add(course);
        course.setInstructor(this);

    }

}
