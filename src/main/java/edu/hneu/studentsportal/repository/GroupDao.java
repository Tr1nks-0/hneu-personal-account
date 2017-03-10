package edu.hneu.studentsportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.hneu.studentsportal.entity.Group;

@Repository
public interface GroupDao extends CrudRepository<Group, String> {
}