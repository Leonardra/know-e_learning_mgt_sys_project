package com.ehizman.know.service;

import com.ehizman.know.data.dto.InstructorLearningPartyDto;
import com.ehizman.know.data.model.Authority;
import com.ehizman.know.data.model.Instructor;
import com.ehizman.know.data.model.LearningParty;
import com.ehizman.know.data.model.Role;
import com.ehizman.know.data.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{
    InstructorRepository repository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository repository){
        this.repository = repository;
    }
   @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Override
    public Instructor save(InstructorLearningPartyDto instructorDto) {
        if (instructorDto == null){
            throw new IllegalArgumentException("Instructor cannot be null object");
        }
        LearningParty learningParty = new LearningParty
                (instructorDto.getEmail(), passwordEncoder.encode(instructorDto.getPassword()),
                        new Authority(Role.ROLE_INSTRUCTOR));
        Instructor instructor = Instructor.builder()
                .firstName(instructorDto.getFirstName())
                .lastName(instructorDto.getLastName())
                .learningParty(learningParty).build();

        //mail_sender asynchronous sending of mail
        return repository.save(instructor);
    }
}
