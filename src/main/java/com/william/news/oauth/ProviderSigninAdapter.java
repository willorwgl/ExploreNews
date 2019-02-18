package com.william.news.oauth;

import com.william.news.domain.NewsUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@Component
public class ProviderSigninAdapter implements SignInAdapter {

    @Override
    public String signIn(String s, Connection<?> connection, NativeWebRequest nativeWebRequest) {
        NewsUser newsUser = SocialAuthenticationUtils.createNewsUser(connection);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        newsUser,
                        null,
                        newsUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return null;
    }
}
