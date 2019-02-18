package com.william.news.oauth;

import com.william.news.domain.NewsUser;
import com.william.news.dataaccess.NewsUserService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class ProviderConnectionSignup implements ConnectionSignUp {

    private final NewsUserService newsUserService;

    public ProviderConnectionSignup(NewsUserService newsUserService) {
        this.newsUserService = newsUserService;
    }

    @Override
    public String execute(Connection<?> connection) {
            NewsUser newsUser = SocialAuthenticationUtils.createNewsUser(connection);
            newsUserService.createUser(newsUser);
            return newsUser.getUsername();
    }
}
