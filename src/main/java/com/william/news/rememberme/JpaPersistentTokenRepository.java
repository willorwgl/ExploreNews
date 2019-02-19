package com.william.news.rememberme;

import com.google.common.base.Preconditions;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class JpaPersistentTokenRepository implements PersistentTokenRepository {

    private RememberMeRepository rememberMeRepository;

    public JpaPersistentTokenRepository(RememberMeRepository rememberMeRepository) {
        Preconditions.checkNotNull(rememberMeRepository, "RememberMeRepository cannot be null");
        this.rememberMeRepository = rememberMeRepository;
    }


    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin newToken = new PersistentLogin(token);
        this.rememberMeRepository.save(newToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLogin token = rememberMeRepository.findBySeries(series);
        if (token != null) {
            token.setToken(tokenValue);
            token.setLastUsed(lastUsed);
            this.rememberMeRepository.save(token);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLogin token = this.rememberMeRepository.findBySeries(seriesId);
        return new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getToken(), token.getLastUsed());
    }

    @Override
    public void removeUserTokens(String username) {
        List<PersistentLogin> tokens = this.rememberMeRepository.findByUsername(username);
        this.rememberMeRepository.deleteAll(tokens);
    }
}
