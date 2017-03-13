package edu.hneu.studentsportal.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import edu.hneu.studentsportal.entity.User;

public interface CustomUserDetailsService extends UserDetailsService {

    List<GrantedAuthority> getGrantedAuthorities(User user);

    Optional<String> extractUserEmail(Map<String, List<Object>> userDetails);

}
