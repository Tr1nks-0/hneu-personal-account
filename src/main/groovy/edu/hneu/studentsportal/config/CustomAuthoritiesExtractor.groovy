package edu.hneu.studentsportal.config

import com.google.common.collect.Lists
import edu.hneu.studentsportal.service.CustomUserDetailsService
import edu.hneu.studentsportal.service.UserService
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

import javax.annotation.Resource

@Component
class CustomAuthoritiesExtractor implements AuthoritiesExtractor {

    @Resource
    CustomUserDetailsService userDetailsService
    @Resource
    UserService userService

    @Override
    List<GrantedAuthority> extractAuthorities(Map<String, Object> userDetails) {
        userDetailsService.extractUserEmail(userDetails)
                .map { email -> userService.getUserForId(email) }
                .map { user -> userDetailsService.getGrantedAuthorities(user)}
                .orElse(Lists.newArrayList(new SimpleGrantedAuthority("ROLE_STUDENT")))
    }

}
