package edu.hneu.studentsportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.hneu.studentsportal.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}