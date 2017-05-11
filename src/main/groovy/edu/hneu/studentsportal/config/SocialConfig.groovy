package edu.hneu.studentsportal.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.*
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.web.filter.CompositeFilter

import javax.servlet.Filter

@Configuration
@EnableOAuth2Client
@EnableAspectJAutoProxy
class SocialConfig {

    @Autowired
    AuthoritiesExtractor authoritiesExtractor

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
    Filter ssoFilter(OAuth2ClientContext oauth2ClientContext) {
        new CompositeFilter(
                filters: [oAuth2ClientAuthenticationProcessingFilter(oauth2ClientContext)]
        )
    }

    Filter oAuth2ClientAuthenticationProcessingFilter(OAuth2ClientContext oauth2ClientContext) {
        def clientAuthenticationProcessingFilter = new OAuth2ClientAuthenticationProcessingFilter('/connect/google')
        clientAuthenticationProcessingFilter.restTemplate = new OAuth2RestTemplate(google().client, oauth2ClientContext)
        clientAuthenticationProcessingFilter.tokenServices = userInfoTokenServices(oauth2ClientContext)
        clientAuthenticationProcessingFilter
    }

    @Bean
    UserInfoTokenServices userInfoTokenServices(OAuth2ClientContext oauth2ClientContext) {
        def tokenServices = new UserInfoTokenServices(google().resource.userInfoUri, google().client.clientId)
        tokenServices.authoritiesExtractor = authoritiesExtractor
        tokenServices.restTemplate = new OAuth2RestTemplate(google().client, oauth2ClientContext)
        tokenServices
    }

    @Bean
    @Scope(value="session", proxyMode = ScopedProxyMode.INTERFACES)
    OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oauth2ClientContext) {
        new OAuth2RestTemplate(google().client, oauth2ClientContext)
    }

    private class ClientResources {

        @NestedConfigurationProperty
        AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails()

        @NestedConfigurationProperty
        ResourceServerProperties resource = new ResourceServerProperties()
    }

}

