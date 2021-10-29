package com.ehizman.know.data.repository;

import com.ehizman.know.data.model.LearningParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningPartyRepository extends JpaRepository<LearningParty, Long> {
    LearningParty findByEmail(String email);

    @Query(value = "select * from learning_party as L where L.email =:email", nativeQuery = true)
    LearningParty findUserByEmail(String email);

}
