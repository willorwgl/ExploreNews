package com.william.news.dataaccess;

import com.william.news.dataaccess.NewsUserRepository;
import com.william.news.dataaccess.NewsUserService;
import com.william.news.domain.NewsUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class NewsUserServiceImpl implements NewsUserService {

    private final NewsUserRepository newsUserRepository;
    private final PasswordEncoder passwordEncoder;

    public NewsUserServiceImpl(NewsUserRepository newsUserRepository, PasswordEncoder passwordEncoder) {
        this.newsUserRepository = newsUserRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public NewsUser findUserByUsername(String username) {
        return newsUserRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public void createUser(NewsUser user) {
        String encodePassword  = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        newsUserRepository.save(user);
    }
}
