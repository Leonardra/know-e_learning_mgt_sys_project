package com.ehizman.know.service;

import com.ehizman.know.data.dto.InstructorLearningPartyDto;
import com.ehizman.know.data.model.Instructor;

public interface InstructorService {
    Instructor save(InstructorLearningPartyDto instructor);
}
