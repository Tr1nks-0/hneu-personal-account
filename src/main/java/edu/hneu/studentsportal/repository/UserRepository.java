package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByRole(UserRole role);
}
