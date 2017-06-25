package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.enums.UserRole;

import java.util.List;

public interface UserService {

    void save(User user);

    User getUserForId(String id);

    List<User> getAdmins();

    User create(String id, UserRole role);
}
