package edu.hneu.studentsportal.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.filter.CompositeFilter

import javax.servlet.Filter

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableAspectJAutoProxy
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthoritiesExtractor authoritiesExtractor
    @Autowired
    OAuth2ClientContext oauth2ClientContext

    @Override
    void configure(HttpSecurity http) {
        http.authorizeRequests()
                .antMatchers('/management/**', '/togglz')
                    .access("hasRole('ROLE_ADMIN')")
                .antMatchers('/account/**')
                    .access("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN')")
                .and()
                    .formLogin()
                        .loginPage('/login')
                        .defaultSuccessUrl('/account', false)
                        .failureUrl('/login?error=true')
                .and()
                    .logout()
                        .logoutSuccessUrl('/login')
                .and()
                    .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                        .exceptionHandling()
                            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint('/'))
                .and()
                    .csrf()
                        .disable()
    }

    @Bean
    FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        new FilterRegistrationBean(
                filter: filter,
                order: -100
        )
    }

    @Bean
    @ConfigurationProperties('google')
    ClientResources google() {
        new ClientResources()
    }

    @Bean
    Filter ssoFilter() {
        new CompositeFilter(
                filters: [oAuth2ClientAuthenticationProcessingFilter()]
        )
    }

    Filter oAuth2ClientAuthenticationProcessingFilter() {
        def clientAuthenticationProcessingFilter = new OAuth2ClientAuthenticationProcessingFilter('/connect/google')
        clientAuthenticationProcessingFilter.restTemplate = new OAuth2RestTemplate(google().client, oauth2ClientContext)
        clientAuthenticationProcessingFilter.tokenServices = userInfoTokenServices()
        clientAuthenticationProcessingFilter
    }

    @Bean
    UserInfoTokenServices userInfoTokenServices() {
        def tokenServices = new UserInfoTokenServices(google().resource.userInfoUri, google().client.clientId)
        tokenServices.authoritiesExtractor = authoritiesExtractor
        tokenServices.restTemplate = new OAuth2RestTemplate(google().client, oauth2ClientContext)
        tokenServices
    }

    @Bean
    @Scope(value="session", proxyMode = ScopedProxyMode.INTERFACES)
    OAuth2RestTemplate oAuth2RestTemplate() {
        new OAuth2RestTemplate(google().client, oauth2ClientContext)
    }

    private class ClientResources {

        @NestedConfigurationProperty
        AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails()

        @NestedConfigurationProperty
        ResourceServerProperties resource = new ResourceServerProperties()
    }

}
