package com.ehizman.know.service;

import com.ehizman.know.data.dto.InstructorLearningPartyDto;
import com.ehizman.know.data.model.Instructor;
import com.ehizman.know.exception.UserAlreadyExistsException;

public interface InstructorService {
    Instructor save(InstructorLearningPartyDto instructor) throws UserAlreadyExistsException;
}
