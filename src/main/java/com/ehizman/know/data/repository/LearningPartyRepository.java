package com.ehizman.know.data.repository;

import com.ehizman.know.data.model.Instructor;
import com.ehizman.know.data.model.LearningParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningPartyRepository extends JpaRepository<LearningParty, Long> {

}
