package edu.hneu.studentsportal.config

import edu.hneu.studentsportal.feature.SiteFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.togglz.core.Feature
import org.togglz.core.manager.TogglzConfig
import org.togglz.core.repository.StateRepository
import org.togglz.core.repository.jdbc.JDBCStateRepository
import org.togglz.core.user.UserProvider
import org.togglz.spring.security.SpringSecurityUserProvider

import javax.annotation.Resource
import javax.sql.DataSource

@Configuration
class DatabaseTogglzConfiguration implements TogglzConfig {

    @Resource
    private DataSource dataSource

    @Override
    Class<? extends Feature> getFeatureClass() {
        SiteFeature.class
    }

    @Bean
    @Override
    StateRepository getStateRepository() {
        new JDBCStateRepository(dataSource, 'togglz')
    }

    @Bean
    @Override
    UserProvider getUserProvider() {
        new SpringSecurityUserProvider('ROLE_ADMIN')
    }
}