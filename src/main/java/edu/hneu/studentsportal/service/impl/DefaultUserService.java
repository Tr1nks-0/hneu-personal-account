package edu.hneu.studentsportal.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import edu.hneu.studentsportal.dao.UserRepository;
import edu.hneu.studentsportal.model.User;
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
    public Optional<User> getUserForId(final String id){
        try {
            return Optional.ofNullable(userRepository.findOne(id));
        } catch (final RuntimeException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public Optional<String> extractUserEmailFromDetails(final LinkedHashMap userDetails) {
        final List emails = (List) userDetails.get("emails");
        if (CollectionUtils.isNotEmpty(emails)) {
            return Optional.ofNullable((String) ((LinkedHashMap) emails.get(0)).get("value"));
        }
        return Optional.empty();
    }

}
