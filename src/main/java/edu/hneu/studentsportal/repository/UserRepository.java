package edu.hneu.studentsportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hneu.studentsportal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
