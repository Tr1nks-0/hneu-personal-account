package com.rozdolskyi.traininghneu.service.impl;

import com.rozdolskyi.traininghneu.dao.UserDao;
import com.rozdolskyi.traininghneu.model.User;
import com.rozdolskyi.traininghneu.service.UserService;
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
}
