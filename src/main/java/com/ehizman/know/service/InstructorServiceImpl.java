package com.ehizman.know.service;

import com.ehizman.know.data.dto.InstructorLearningPartyDto;
import com.ehizman.know.data.model.Authority;
import com.ehizman.know.data.model.Instructor;
import com.ehizman.know.data.model.LearningParty;
import com.ehizman.know.data.model.Role;
import com.ehizman.know.data.repository.InstructorRepository;
import com.ehizman.know.data.repository.LearningPartyRepository;
import com.ehizman.know.exception.UserAlreadyExistsException;
import com.ehizman.know.service.events.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService{
    private InstructorRepository instructorRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LearningPartyRepository learningPartyRepository;
    private ApplicationEventPublisher eventPublisher;


    @Autowired
    public InstructorServiceImpl(InstructorRepository repository,
                                 BCryptPasswordEncoder passwordEncoder,
                                 LearningPartyRepository learningPartyRepository,
                                 ApplicationEventPublisher eventPublisher){
        this.instructorRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.learningPartyRepository = learningPartyRepository;
        this.eventPublisher = eventPublisher;
    }
    @Override
    public Instructor save(InstructorLearningPartyDto instructorDto) throws UserAlreadyExistsException {
        if (instructorDto == null){
            throw new IllegalArgumentException("Instructor cannot be null object");
        }
        if (learningPartyRepository.findByEmail(instructorDto.getEmail()) != null){
            throw new UserAlreadyExistsException(String.format("User with email %s already exists", instructorDto.getEmail()));
        }
        LearningParty learningParty = new LearningParty
                (instructorDto.getEmail(), passwordEncoder.encode(instructorDto.getPassword()),
                        new Authority(Role.ROLE_INSTRUCTOR));
        Instructor instructor = Instructor.builder()
                .firstName(instructorDto.getFirstName())
                .lastName(instructorDto.getLastName())
                .learningParty(learningParty).build();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(learningParty));
        //mail_sender asynchronous sending of mail
        return instructorRepository.save(instructor);
    }
}
