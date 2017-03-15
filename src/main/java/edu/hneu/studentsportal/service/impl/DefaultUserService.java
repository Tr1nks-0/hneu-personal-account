package edu.hneu.studentsportal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import edu.hneu.studentsportal.entity.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.UserRepository;
import edu.hneu.studentsportal.service.UserService;

@Service
public class DefaultUserService implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public void save(final User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserForId(final String id){
        return userRepository.findOne(id);
    }

    @Override
    public void create(String id, UserRole role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        save(user);
    }

}
