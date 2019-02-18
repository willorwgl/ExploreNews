package com.william.news.oauth;

import com.william.news.domain.NewsUser;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class SocialAuthenticationUtils {

    public static NewsUser createNewsUser(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        NewsUser newsUser = new NewsUser();
        if (userProfile.getUsername() != null) {
            newsUser.setUsername(userProfile.getUsername());
        } else if (userProfile.getEmail() != null) {
            newsUser.setUsername(userProfile.getEmail());
        } else {
            newsUser.setUsername(connection.getDisplayName());
        }
        newsUser.setPassword(randomAlphabetic(32));
        newsUser.setAuthority("USER");
        return newsUser;
    }
}
