package edu.hneu.studentsportal.service.impl;

import edu.hneu.studentsportal.dao.UserDao;
import edu.hneu.studentsportal.model.User;
import edu.hneu.studentsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User getUserForId(String id){
        try {
            return userDao.findOne(id);
        } catch (RuntimeException e) {
            return null;
        }
    }

}
