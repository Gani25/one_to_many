package com.sprk.one_to_many.repository;

import com.sprk.one_to_many.entity.Course;
import com.sprk.one_to_many.entity.Instructor;
import com.sprk.one_to_many.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@AllArgsConstructor
public class AppDao {


    private final EntityManager entityManager;

    @Transactional
    public Instructor saveInstructor(Instructor instructor) {
        return entityManager.merge(instructor);
    }

    @Transactional
    public InstructorDetail saveInstructorDetail(InstructorDetail instructorDetail) {
        return entityManager.merge(instructorDetail);
    }

    public Instructor getInstructorById(int id) {
        Instructor instructor = entityManager.find(Instructor.class, id);

//        Hibernate.initialize(instructor.getCourses()); // Ensure courses are loaded only when needed
        return instructor;
    }

    @Transactional
    public String deleteInstructorById(int instructorId) {
        Instructor instructor = getInstructorById(instructorId);
        if (instructor != null) {
            List<Course> courses = instructor.getCourses();
            for (Course course : courses) {
                course.setInstructor(null);
            }
            entityManager.remove(instructor);
            return "Deleted successfully";
        } else {
            return "Not Found!!";
        }

    }

    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = entityManager.createQuery("from Instructor").getResultList();

        return instructors;
    }

    public InstructorDetail findInstructorDetailById(int instructorDetailId) {

        return entityManager.find(InstructorDetail.class, instructorDetailId);
    }

    @Transactional
    public String deleteInstructorDetailById(int instructorDetailId) {

        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, instructorDetailId);
        if (instructorDetail != null) {
            Instructor instructor = instructorDetail.getInstructor();
            instructor.setInstructorDetail(null);
            entityManager.remove(instructorDetail);
            return "Deleted successfully";
        } else {
            return "Not Found!!";
        }
    }

    @Transactional
    public Instructor updateInstrutorDetail(int instructorId, InstructorDetail instructorDetail) {

        Instructor instructor = entityManager.find(Instructor.class, instructorId);
        if(instructor != null) {
            instructor.setInstructorDetail(instructorDetail);
            return  entityManager.merge(instructor);
        }else{
            return  null;
        }
    }

    public List<Course> findCoursesByInstructorId(int instructorId) {

        TypedQuery<Course> query =  entityManager.createQuery("from Course where instructor.instructorId = :data", Course.class);

        query.setParameter("data", instructorId);

        List<Course> courses = query.getResultList();

        return courses;
    }

    public Instructor findInstructorJoinFetch(int instructorId) {

        TypedQuery<Instructor> query =  entityManager.createQuery("from Instructor i join fetch i.courses join fetch i.instructorDetail  where i.instructorId = :data", Instructor.class);

        query.setParameter("data", instructorId);

        Instructor instructor = query.getSingleResult();
        return instructor;
    }

    @Transactional
    public String updateCourse(int courseId, Course course) {

        Course dbCOurse = entityManager.find(Course.class, courseId);
        if(dbCOurse != null) {
            course.setCourseId(courseId);

            entityManager.merge(course);
            return "Update successfull";
        }

        return "Course with id " + courseId + " not found";
    }

    public List<Course> findAllCourses() {

        List<Course> courses = entityManager.createQuery("from Course").getResultList();

        return courses;
    }
}
