package com.william.news.dataaccess;

import com.william.news.domain.NewsUser;

public interface NewsUserService {
    NewsUser findUserByUsername(String username);
    void createUser(NewsUser user);
}
