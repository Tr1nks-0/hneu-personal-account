package edu.hneu.studentsportal.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import edu.hneu.studentsportal.domain.User;

public interface CustomUserDetailsService extends UserDetailsService {

    List<GrantedAuthority> getGrantedAuthorities(User user);

    String extractUserEmail(Map<String, List<Object>> userDetails);

    String extractUserEmail(Principal principal);
}
