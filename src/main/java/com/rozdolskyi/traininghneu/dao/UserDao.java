package com.rozdolskyi.traininghneu.dao;

import com.rozdolskyi.traininghneu.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}