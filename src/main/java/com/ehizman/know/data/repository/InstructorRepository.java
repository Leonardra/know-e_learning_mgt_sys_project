package com.ehizman.know.data.repository;

import com.ehizman.know.data.model.Course;
import com.ehizman.know.data.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
