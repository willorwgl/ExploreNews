package com.william.news.restclient;

import com.william.news.domain.Article;
import com.william.news.domain.EverythingSearchForm;
import com.william.news.domain.HeadlineSearchForm;
import com.william.news.domain.SearchResult;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

@Component
public class NewsApiClient {

    private final String headlinesUrl;
    private final String everythingUrl;
    private final String key;
    private final RestTemplate restTemplate;
    private final int FIXED_PAGE_SIZE = 100;

    public NewsApiClient(final RestTemplateBuilder restTemplateBuilder, final NewsApiProperties properties) {
         this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(properties.getConnectTimeout()))
                .setReadTimeout(Duration.ofSeconds(properties.getReadTimeout())).build();
                this.headlinesUrl = properties.getHeadlinesUrl();
                this.everythingUrl = properties.getEverythingUrl();
                this.key = properties.getKey();
    }

    public Article[] searchFromEverything(EverythingSearchForm searchForm) throws NewsApiException {
        String url = UriComponentsBuilder.fromUriString(this.everythingUrl)
                .queryParam("q", searchForm.getKeyword())
                .queryParam("sources", searchForm.getSources())
                .queryParam("from", searchForm.getFrom())
                .queryParam("to", searchForm.getTo())
                .queryParam("sortBy", searchForm.getSortBy())
                .queryParam("pageSize", this.FIXED_PAGE_SIZE)
                .queryParam("apikey", this.key)
                .build()
                .encode()
                .toUriString();
        SearchResult searchResult = restTemplate.getForObject(url, SearchResult.class);
        if (!searchResult.getStatus().equals("ok")) {
            throw new NewsApiException();
        }
        Article[] articles = searchResult.getArticles();
        return articles.length != 0 ? articles : null;
    }

    public Article[] searchFromHeadlines(HeadlineSearchForm searchForm) throws NewsApiException{
       UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(this.headlinesUrl)
               .queryParam("q", searchForm.getKeyword())
               .queryParam("pageSize", this.FIXED_PAGE_SIZE)
               .queryParam("apikey", this.key);
       if (searchForm.getCategory() != null || searchForm.getCountry() != null) {
           uriComponentsBuilder.queryParam("country", searchForm.getCountry()).queryParam("category", searchForm.getCategory());
       } else {
           uriComponentsBuilder.queryParam("sources", searchForm.getSources());
       }
       String url = uriComponentsBuilder.build().encode().toUriString();
        SearchResult searchResult = restTemplate.getForObject(url, SearchResult.class);
        if (!searchResult.getStatus().equals("ok")) {
            throw new NewsApiException();
        }
        Article[] articles = searchResult.getArticles();
        return articles.length != 0 ? articles : null;
    }
}
