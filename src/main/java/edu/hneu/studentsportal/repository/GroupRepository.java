package edu.hneu.studentsportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hneu.studentsportal.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    Optional<Group> findByName(String name);

}
