package com.ehizman.know.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.hateoas.RepresentationModel;

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
public class Instructor  extends RepresentationModel<Instructor>{

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

    @JoinColumn(nullable = false)
    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties
    private LearningParty learningParty;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses;
}
