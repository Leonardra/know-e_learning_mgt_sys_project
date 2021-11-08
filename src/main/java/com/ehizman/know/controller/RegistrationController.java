package com.ehizman.know.controller;

import com.ehizman.know.data.dto.InstructorLearningPartyDto;
import com.ehizman.know.data.model.Instructor;
import com.ehizman.know.exception.UserAlreadyExistsException;
import com.ehizman.know.service.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class RegistrationController {
    private InstructorService instructorService;

    @Autowired
    public RegistrationController(InstructorService instructorService) {

        this.instructorService = instructorService;
    }

    @PostMapping("/instructors/save")
    public ResponseEntity<?> registerAsAnInstructor(@RequestBody InstructorLearningPartyDto dto){
        try {
            Instructor instructorData = instructorService.save(dto);
            return ResponseEntity.ok().body(instructorData.add());
        } catch (UserAlreadyExistsException e) {
            log.info("Exception --> {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

