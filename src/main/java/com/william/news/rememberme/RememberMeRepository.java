package com.william.news.rememberme;

import com.william.news.rememberme.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RememberMeRepository extends JpaRepository<PersistentLogin, String> {

    PersistentLogin findBySeries(String series);
    List<PersistentLogin> findByUsername(String username);
}
