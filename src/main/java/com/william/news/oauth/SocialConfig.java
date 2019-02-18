package com.william.news.oauth;


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

import javax.sql.DataSource;

@Configuration
public class SocialConfig {

    private ProviderConnectionSignup providerConnectionSignup;
    private SignInAdapter signInAdapter;
    private Environment environment;
    private DataSource dataSource;

    public SocialConfig(ProviderConnectionSignup providerConnectionSignup, SignInAdapter signInAdapter,
                        Environment environment, DataSource dataSource) {

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
                environment.getProperty("spring.social.facebook.appSecret")
        ));
        return registry;
    }



}
