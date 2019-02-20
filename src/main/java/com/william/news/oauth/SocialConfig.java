package com.william.news.oauth;


import com.google.common.base.Preconditions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import javax.sql.DataSource;

@Configuration
public class SocialConfig {

    private final ProviderConnectionSignup providerConnectionSignup;
    private final SignInAdapter signInAdapter;
    private final Environment environment;
    private final DataSource dataSource;

    public SocialConfig(ProviderConnectionSignup providerConnectionSignup, SignInAdapter signInAdapter,
                        Environment environment, DataSource dataSource) {
        Preconditions.checkNotNull(providerConnectionSignup, "ProviderConnectionSignup cannot be null");
        Preconditions.checkNotNull(signInAdapter, "SignInAdapter cannot be null");
        Preconditions.checkNotNull(dataSource, "DataSource cannot be null");
        Preconditions.checkNotNull(environment, "Environment cannot be null");
        this.providerConnectionSignup = providerConnectionSignup;
        this.signInAdapter = signInAdapter;
        this.environment = environment;
        this.dataSource = dataSource;
    }


    @Bean
    public ProviderSignInController providerSignInController() {
        UsersConnectionRepository usersConnectionRepository = usersConnectionRepository(connectionFactoryLocator());
        usersConnectionRepository.setConnectionSignUp(providerConnectionSignup);
        return new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository, signInAdapter);
    }

    public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        TextEncryptor textEncryptor = Encryptors.noOpText();
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor);
    }

    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory(
                environment.getProperty("spring.social.facebook.appId"),
                environment.getProperty("spring.social.facebook.appSecret")));
        registry.addConnectionFactory(new TwitterConnectionFactory(
                environment.getProperty("spring.social.twitter.appId"),
                environment.getProperty("spring.social.twitter.appSecret")));
        registry.addConnectionFactory(new GoogleConnectionFactory(
                environment.getProperty("spring.social.google.appId"),
                environment.getProperty("spring.social.google.appSecret")));
        return registry;
    }



}
