package edu.hneu.studentsportal.dao;

import edu.hneu.studentsportal.model.Group;
import edu.hneu.studentsportal.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface GroupDao extends CrudRepository<Group, String> {
}