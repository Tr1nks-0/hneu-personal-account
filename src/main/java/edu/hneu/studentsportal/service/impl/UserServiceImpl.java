package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.UserRepository;
import edu.hneu.studentsportal.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserForId(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User create(String id, UserRole role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        save(user);
        return user;
    }

}
