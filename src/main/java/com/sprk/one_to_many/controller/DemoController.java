package com.sprk.one_to_many.controller;

import com.sprk.one_to_many.entity.Course;
import com.sprk.one_to_many.entity.Instructor;
import com.sprk.one_to_many.entity.InstructorDetail;
import com.sprk.one_to_many.repository.AppDao;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DemoController {

    private final AppDao appDao;

    @PostMapping("/save")
    public Instructor saveInstructor(@RequestBody Instructor instructor) {

        List<Course> courses = instructor.getCourses();

        for (Course course : courses) {
            course.setInstructor(instructor);
        }

        return appDao.saveInstructor(instructor);
    }

    @GetMapping("/getbyid/{instructorId}")
    public Instructor getInstructorById(@PathVariable int instructorId) {

        Instructor instructor = appDao.getInstructorById(instructorId);

        System.out.println(instructor);
//        System.out.println("Courses:- " +instructor.getCourses());
        return instructor;
    }

    @GetMapping("/getall")
    public List<Instructor> getAllInstructors() {
        return appDao.getAllInstructors();
    }

    @DeleteMapping("/deletebyid/{instructorId}")
    public String deleteByInstructorId(@PathVariable int instructorId) {
        String message = appDao.deleteInstructorById(instructorId);

        return message;

    }

    @PutMapping("/updatebyid/{instructorId}")
    public Instructor updateByInstructorId(@PathVariable int instructorId, @RequestBody Instructor instructor) {
        Instructor savedInstructor = appDao.getInstructorById(instructorId);
        if (savedInstructor != null) {
            InstructorDetail updatedInstructorDetail = instructor.getInstructorDetail();
            updatedInstructorDetail.setInstructorDetailId(savedInstructor.getInstructorDetail().getInstructorDetailId());

            instructor.setInstructorId(savedInstructor.getInstructorId());
            instructor.setInstructorDetail(updatedInstructorDetail);

            return appDao.saveInstructor(instructor);


        } else {
            return null;
        }
    }

    @GetMapping("/find-detail/{instructorDetailId}")
    public InstructorDetail getInstructorDetailById(@PathVariable int instructorDetailId) {

        return appDao.findInstructorDetailById(instructorDetailId);
    }

    @PostMapping("/saveDetail")
    public Instructor saveInstructorDetail(@RequestBody InstructorDetail instructorDetail) {

        Instructor instructor = instructorDetail.getInstructor();
        instructor.setInstructorDetail(instructorDetail);

        return appDao.saveInstructor(instructor);

    }

    @DeleteMapping("/delete-detail/{instructorDetailId}")
    public String deleteByInstructorDetailId(@PathVariable int instructorDetailId) {
        String message = appDao.deleteInstructorDetailById(instructorDetailId);

        return message;

    }

    @PutMapping("/update-detail/{instructorId}")
    public Instructor updateInstructorDetail(@PathVariable int instructorId, @RequestBody InstructorDetail instructorDetail) {

        return appDao.updateInstrutorDetail(instructorId, instructorDetail);
    }

    //    Finding courses based on instructorId
    @GetMapping("/getcoursesbyid/{instructorId}")
    public List<Course> getCoursesByInstructorId(@PathVariable int instructorId) {

        List<Course> courses= appDao.findCoursesByInstructorId(instructorId); // Instructor + InstructorDetail

        return courses;
    }

    @GetMapping("/getinstructorjoinfetch/{instructorId}")
    public Instructor getInstructorJoinFetch(@PathVariable int instructorId) {

        Instructor instructor= appDao.findInstructorJoinFetch(instructorId); // Instructor + InstructorDetail



        return instructor;
    }

    @GetMapping("/getcourses")
    public List<Course> getCourses() {
        return appDao.findAllCourses();
    }

    @PutMapping("/updatecourse/{courseId}")
    public String updateCourse(@PathVariable int courseId, @RequestBody Course course) {

        return appDao.updateCourse(courseId, course);

    }

}


