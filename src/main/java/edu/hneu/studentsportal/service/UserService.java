package edu.hneu.studentsportal.service;

import edu.hneu.studentsportal.domain.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User getUserForId(String id) {
        return userRepository.findOne(id);
    }

    public List<User> getAdmins() {
        return userRepository.findAllByRole(UserRole.ADMIN);
    }

    public User create(String id, UserRole role) {
        User user = new User();
        user.setId(id);
        user.setRole(role);
        save(user);
        return user;
    }

}
