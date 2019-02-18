package com.william.news.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {


    private Source source;
    private String author;
    private String description;
    private String title;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

}
