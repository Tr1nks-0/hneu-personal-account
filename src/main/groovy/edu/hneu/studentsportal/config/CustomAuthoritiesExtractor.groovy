package edu.hneu.studentsportal.config

import edu.hneu.studentsportal.enums.UserRole
import edu.hneu.studentsportal.service.UserService
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

import javax.annotation.Resource

@Component
class CustomAuthoritiesExtractor implements AuthoritiesExtractor {

    @Resource
    UserService userService

    @Override
    List<GrantedAuthority> extractAuthorities(final Map<String, Object> userDetails) {
        [ getSimpleGrantedAuthority((LinkedHashMap) userDetails) ]
    }

    def getSimpleGrantedAuthority(final LinkedHashMap userDetails) {
        def email = userService.extractUserEmailFromDetails(userDetails)
        if (email) {
            def user = userService.getUserForId(email.get())
            if (user.isPresent() && user.get().getRole() == UserRole.ADMIN) {
                return new SimpleGrantedAuthority("ROLE_ADMIN")
            } else {
                return new SimpleGrantedAuthority("ROLE_USER")
            }
        } else {
            return new SimpleGrantedAuthority("ROLE_USER")
        }
    }
}
