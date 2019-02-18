package com.william.news.dataaccess;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NewsUserDetailsServiceImpl implements UserDetailsService {

    private NewsUserService newsUserService;

    public NewsUserDetailsServiceImpl(NewsUserService newsUserService) {
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
