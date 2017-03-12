package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByLabel(String courseLabel);
}
