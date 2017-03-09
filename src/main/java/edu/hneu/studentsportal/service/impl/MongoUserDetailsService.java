package edu.hneu.studentsportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.hneu.studentsportal.dao.UserDao;
import edu.hneu.studentsportal.model.User;

@Component
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    private org.springframework.security.core.userdetails.User userdetails;

    public UserDetails loadUserByUsername(final String id) throws UsernameNotFoundException {
        final boolean enabled = true;
        final boolean accountNonExpired = true;
        final boolean credentialsNonExpired = true;
        final boolean accountNonLocked = true;
        final User user = getUserDetail(id);
        userdetails = new org.springframework.security.core.userdetails.User(user.getId(),
                null,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(user.getRole().ordinal()));
        return userdetails;
    }

    public List<GrantedAuthority> getAuthorities(final Integer role) {
        final List<GrantedAuthority> authList = new ArrayList<>();
        if (role.intValue() == 1) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (role.intValue() == 2) {
            authList.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }
        return authList;
    }

    public User getUserDetail(final String id) {
        final User user = userDao.findOne(id);
        return user;
    }
}
