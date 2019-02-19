package com.william.news.dataaccess;

import com.google.common.base.Preconditions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NewsUserDetailsServiceImpl implements UserDetailsService {

    private NewsUserService newsUserService;

    public NewsUserDetailsServiceImpl(NewsUserService newsUserService) {
        Preconditions.checkNotNull(newsUserService, "NewsUserService cannot be null");
        this.newsUserService = newsUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = newsUserService.findUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Invalid username/password");
        }
        return  userDetails;
    }


}
