package com.ehizman.know.data.repository;

import com.ehizman.know.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j

@Sql(scripts = "/db/insert.sql")
class InstructorRepositoryTest {
    private InstructorRepository repository;
    @Autowired
    public InstructorRepositoryTest(InstructorRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_saveInstructorAsLearningParty(){
        LearningParty user = new LearningParty("trainer@know.com", "1234password", new Authority(Role.ROLE_INSTRUCTOR));
        Instructor instructor = Instructor.builder().firstName("Peter")
                .lastName("Alao").learningParty(user).build();

        log.info("Instructor before saving --> {}", instructor);
        repository.save(instructor);
        log.info("Instructor after saving --> {}", instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getFirstName()).isEqualTo("Peter");
        assertThat(instructor.getLastName()).isEqualTo("Alao");
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("Learning Party after saving --> {}", instructor.getLearningParty());
    }


    @Test
    @Transactional
    @Rollback(value = false)
    @Sql(scripts = "/db/insert.sql")
    void canUpdateInstructorInformation(){
        LearningParty user = new LearningParty("student@know.gmail.com", "password", new Authority(Role.ROLE_STUDENT));
        Instructor instructor = new Instructor("John", "Alao", user, new Course("Introduction to programming in Assembly Language"));
        repository.save(instructor);

        Instructor fromDb = repository.findById(instructor.getId()).orElse(null);
        assertThat(fromDb).isNotNull();

        Course course = new Course("Introduction to Machine Learning");
        fromDb.setFirstName("John");
        fromDb.setLastName("Lamade");
        fromDb.setBio("John Lamade is a Doctor of Engineering at the University of Benin");
        fromDb.setGender(Gender.MALE);
        fromDb.getCourses().add(course);

        log.info("Before updating instructor --> {}", fromDb);
        repository.save(fromDb);
        log.info("After updating instructor --> {}", fromDb);

        assertThat(fromDb.getFirstName()).isEqualTo("John");
        assertThat(fromDb.getLastName()).isEqualTo("Lamade");
        assertThat(fromDb.getCourses()).hasSize(2);
        assertThat(fromDb.getBio()).isEqualTo("John Lamade is a Doctor of Engineering at the University of Benin");
        assertThat(fromDb.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    void test_ThatInstructorFirstNameAndLastNameCannotBeNull(){
        Instructor instructor = new Instructor(null, null, null, null);
        assertThatThrownBy(()->repository.save(instructor)).isInstanceOf(ConstraintViolationException.class);
    }
    @Test
    void testThatCannotCreateInstructorsWithEmptyStringValues(){
        LearningParty user = new LearningParty("student@know.gmail.com", "password", new Authority(Role.ROLE_STUDENT));
        Instructor instructor = Instructor.builder().firstName(" ").lastName(" ").learningParty(user).courses(List.of(new Course("Machine Language 101"))).build();
        log.info("Before saving --> {}", instructor);
        assertThatThrownBy(()-> repository.save(instructor)).isInstanceOf(ConstraintViolationException.class);
    }

}