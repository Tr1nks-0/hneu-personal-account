package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.enums.UserRole;

public interface UserService {

    void save(User user);

    User getUserForId(String id);

    void create(String id, UserRole role);
}
