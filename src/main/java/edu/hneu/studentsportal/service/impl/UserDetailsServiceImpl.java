package edu.hneu.studentsportal.service.impl;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.hneu.studentsportal.entity.User;
import edu.hneu.studentsportal.enums.UserRole;
import edu.hneu.studentsportal.repository.UserRepository;
import edu.hneu.studentsportal.service.CustomUserDetailsService;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        String password = null;
        User user = userRepository.findOne(id);
        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getGrantedAuthorities(user)
        );
    }

    @Override
    public List<GrantedAuthority> getGrantedAuthorities(User user) {
        String userRole = UserRole.ADMIN.equals(user.getRole()) ? "ROLE_ADMIN" : "ROLE_STUDENT";
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole);
        return Lists.newArrayList(grantedAuthority);
    }

    @Override
    public String extractUserEmail(Map<String, List<Object>> userDetails) {
        return Optional.ofNullable(userDetails.get("emails"))
                .map(List::stream)
                .flatMap(Stream::findFirst)
                .filter(Map.class::isInstance).map(Map.class::cast)
                .map(email -> email.get("value"))
                .filter(String.class::isInstance).map(String.class::cast)
                .orElse(null);
    }

    @Override
    public String extractUserEmail(Principal principal) {
        return extractUserEmail((Map<String, List<Object>>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails());
    }
}
