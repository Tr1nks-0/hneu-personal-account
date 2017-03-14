package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.Student;

import java.util.List;

public interface StudentDao {

    List<Student> find(String fullName, Integer page);

    Student find(String subKey, String groupCode);

    long getPagesCountForSearchCriteria(String searchCriteria);
}
