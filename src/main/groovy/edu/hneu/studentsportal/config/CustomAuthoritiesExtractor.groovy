package edu.hneu.studentsportal.config

import com.google.common.collect.Lists
import edu.hneu.studentsportal.enums.UserRole
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
public class CustomAuthoritiesExtractor implements AuthoritiesExtractor {

    @Override
    public List<GrantedAuthority> extractAuthorities(final Map<String, Object> userDetails) {
        return Lists.newArrayList(getSimpleGrantedAuthority((LinkedHashMap) userDetails));
    }

    private SimpleGrantedAuthority getSimpleGrantedAuthority(final LinkedHashMap userDetails) {
        def email = userService.extractUserEmailFromDetails(userDetails);
        if (email) {
            def user = userService.getUserForId(email.get());
            if (user && user.get().getRole() == UserRole.ADMIN) {
                return new SimpleGrantedAuthority("ROLE_ADMIN");
            } else {
                return new SimpleGrantedAuthority("ROLE_USER");
            }
        } else {
            return new SimpleGrantedAuthority("ROLE_USER");
        }
    }
}
