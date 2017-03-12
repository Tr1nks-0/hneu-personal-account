package edu.hneu.studentsportal.repository;

import edu.hneu.studentsportal.entity.StudentProfile;

import java.util.List;

public interface StudentDao {

    List<StudentProfile> find(String fullName, Integer page);

    StudentProfile find(String subKey, String groupCode);

    long getPagesCountForSearchCriteria(String searchCriteria);
}
