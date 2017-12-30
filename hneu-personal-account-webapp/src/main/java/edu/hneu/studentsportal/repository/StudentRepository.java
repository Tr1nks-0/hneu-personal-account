package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.domain.Group;
import edu.hneu.studentsportal.domain.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    List<Student> findByGroup(Group group, Sort sort);

    Optional<Student> findByNameAndSurnameAndGroup(String name, String surname, Group group);
}
