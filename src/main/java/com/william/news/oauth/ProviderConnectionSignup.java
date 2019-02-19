package com.william.news.oauth;

import com.google.common.base.Preconditions;
import com.william.news.dataaccess.NewsUserService;
import com.william.news.domain.NewsUser;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class ProviderConnectionSignup implements ConnectionSignUp {

    private final NewsUserService newsUserService;

    public ProviderConnectionSignup(NewsUserService newsUserService) {
        Preconditions.checkNotNull(newsUserService, "NewsUserService cannot be null");
        this.newsUserService = newsUserService;
    }

    @Override
    public String execute(Connection<?> connection) {
            NewsUser newsUser = SocialAuthenticationUtils.createNewsUser(connection);
            newsUserService.createUser(newsUser);
            return newsUser.getUsername();
    }
}
