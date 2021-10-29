package com.ehizman.know.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {

    public Instructor(String firstName, String lastName, LearningParty learningParty, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.learningParty = learningParty;
        addCourse(course);
    }

    private void addCourse(Course course) {
        if (this.courses == null){
            this.courses = new ArrayList<>();
        }
        this.courses.add(course);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank @NotNull
    private String firstName;

    @NotBlank @NotNull
    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String specialization;

    @Column(length = 1000)
    private String bio;

    @OneToOne(cascade = CascadeType.PERSIST)
    private LearningParty learningParty;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses;
}
