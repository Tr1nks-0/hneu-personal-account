package edu.hneu.studentsportal.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import edu.hneu.studentsportal.service.UserService;

@Component
public class CustomAuthoritiesExtractor implements AuthoritiesExtractor {

    @Autowired
    private UserService userService;

    @Override
    public List<GrantedAuthority> extractAuthorities(final Map<String, Object> userDetails) {
        return Lists.newArrayList(getSimpleGrantedAuthority((LinkedHashMap) userDetails));
    }

    private SimpleGrantedAuthority getSimpleGrantedAuthority(final LinkedHashMap userDetails) {
        // @formatter:off
        return userService.extractUserEmailFromDetails(userDetails)
                    .map(email -> userService.getUserForId(email)
                        .map(user -> user.getRole() == 1 ? new SimpleGrantedAuthority("ROLE_ADMIN") : null)
                            .orElse(null)
                ).orElse(new SimpleGrantedAuthority("ROLE_USER"));
        // @formatter:on
    }
}
