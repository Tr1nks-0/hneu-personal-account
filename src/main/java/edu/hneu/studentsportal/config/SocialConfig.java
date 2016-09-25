package edu.hneu.studentsportal.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CompositeFilter;

import com.google.common.collect.Lists;

@Configuration
@EnableOAuth2Client
@EnableAuthorizationServer
public class SocialConfig {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;
    @Autowired
    private AuthoritiesExtractor authoritiesExtractor;

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(final OAuth2ClientContextFilter filter) {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }

    @Bean
    public Filter ssoFilter() {
        final CompositeFilter filter = new CompositeFilter();
        filter.setFilters(Lists.newArrayList(ssoFilter(google(), "/connect/google")));
        return filter;
    }

    private Filter ssoFilter(final ClientResources client, final String path) {
        final OAuth2ClientAuthenticationProcessingFilter clientAuthenticationProcessingFilter = new OAuth2ClientAuthenticationProcessingFilter(path);
        final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        clientAuthenticationProcessingFilter.setRestTemplate(oAuth2RestTemplate);
        final UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setAuthoritiesExtractor(authoritiesExtractor);
        tokenServices.setRestTemplate(oAuth2RestTemplate);
        clientAuthenticationProcessingFilter.setTokenServices(tokenServices);
        return clientAuthenticationProcessingFilter;
    }

    private class ClientResources {

        @NestedConfigurationProperty
        private final AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @NestedConfigurationProperty
        private final ResourceServerProperties resource = new ResourceServerProperties();

        public AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        public ResourceServerProperties getResource() {
            return resource;
        }
    }

}

