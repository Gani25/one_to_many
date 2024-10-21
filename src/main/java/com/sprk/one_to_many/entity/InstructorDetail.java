package com.sprk.one_to_many.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InstructorDetail {

    /*
    * ALTER TABLE instructor_details AUTO_INCREMENT = 1001;
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int instructorDetailId;

    private String hobby;

    private String qualification;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH} ,mappedBy = "instructorDetail")
    @JsonBackReference
    private Instructor instructor;


}
