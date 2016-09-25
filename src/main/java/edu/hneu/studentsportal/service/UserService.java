package edu.hneu.studentsportal.service;

import java.util.LinkedHashMap;
import java.util.Optional;

import edu.hneu.studentsportal.model.User;

public interface UserService {

    void save(User user);

    Optional<User> getUserForId(String id);

    Optional<String> extractUserEmailFromDetails(LinkedHashMap userDetails);
}
