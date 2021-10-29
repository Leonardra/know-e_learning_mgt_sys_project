package com.ehizman.know.data.repository;

import com.ehizman.know.data.model.Authority;
import com.ehizman.know.data.model.LearningParty;
import com.ehizman.know.data.model.Role;
import com.ehizman.know.exception.Know_E_LearningException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class LearningPartyRepositoryTest {
    private LearningPartyRepository repository;
    @Autowired
    public LearningPartyRepositoryTest(LearningPartyRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void createLearningPartyTest(){
        LearningParty learningParty = new LearningParty("johndoe@gmail.com", "password", new Authority(Role.ROLE_STUDENT));
        log.info("Before saving -->{}", learningParty);
        repository.save(learningParty);
        log.info("After saving -> {}", learningParty);
        assertThat(learningParty.getId()).isNotNull();
        assertThat(learningParty.getEmail()).isEqualTo("johndoe@gmail.com");
        assertThat(learningParty.getPassword()).isEqualTo("password");
        assertThat(learningParty.getAuthorities().get(0).getAuthority()).isEqualTo(Role.ROLE_STUDENT);
    }

    @Test
    @Transactional
    @Sql(scripts = "/db/delete_user.sql")
    @DisplayName("each learning party should have a unique email")
    void testUniqueEmail(){
        LearningParty firstUser = new LearningParty("janedoe@gmail.com", "password", new Authority(Role.ROLE_INSTRUCTOR));
        log.info("Before saving --> {}", firstUser);
        repository.save(firstUser);

        LearningParty secondUser = new LearningParty("janedoe@gmail.com", "another_password", new Authority(Role.ROLE_STUDENT));

       assertThatThrownBy(()->repository.save(secondUser)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void testThatCannotCreateLearningPartiesWithNullValues(){
        LearningParty firstUser = new LearningParty(null, null, null);
        log.info("Before saving --> {}", firstUser);
        assertThatThrownBy(()-> repository.save(firstUser)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testThatCannotCreateLearningPartiesWithEmptyStringValues(){
        LearningParty firstUser = new LearningParty(" ", " ", new Authority(Role.ROLE_STUDENT));
        log.info("Before saving --> {}", firstUser);
        assertThatThrownBy(()-> repository.save(firstUser)).isInstanceOf(ConstraintViolationException.class);
    }

    @Transactional
    @Rollback(value = false)
    @Test
    void testFindLearningPartyById(){
        LearningParty learningParty = new LearningParty("johndoe@gmail.com", "password", new Authority(Role.ROLE_STUDENT));
        log.info("Before saving -->{}", learningParty);
        repository.save(learningParty);
        LearningParty retrievedFromDatabase = repository.findById(learningParty.getId()).orElse(null);
        log.info("Retrieved from database --> {}", retrievedFromDatabase);
        assertThat(retrievedFromDatabase).isNotNull();
        assertThat(retrievedFromDatabase.getEmail()).isEqualTo("johndoe@gmail.com");
        assertThat(retrievedFromDatabase.getPassword()).isEqualTo("password");
        assertThat(retrievedFromDatabase.getAuthorities().get(0).getAuthority()).isEqualTo(Role.ROLE_STUDENT);
    }
}