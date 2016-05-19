package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.model.User;

public interface UserService {

    void save(User user);

    User getUserForId(String id);
}
