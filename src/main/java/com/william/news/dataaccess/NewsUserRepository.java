package com.william.news.dataaccess;

import com.william.news.domain.NewsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsUserRepository extends JpaRepository<NewsUser, String> {
    NewsUser findByUsernameIgnoreCase(String username);
}
