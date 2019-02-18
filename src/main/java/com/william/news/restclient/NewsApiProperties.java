package com.william.news.restclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "newsapi")
public class NewsApiProperties {

    private int readTimeout;
    private int connectTimeout;
    private String key;
    private String everythingUrl;
    private String headlinesUrl;

}
