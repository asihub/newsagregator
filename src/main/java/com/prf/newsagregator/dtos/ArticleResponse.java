package com.prf.newsagregator.dtos;

import com.prf.newsagregator.entities.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Response coming from news API sources
 */
public class ArticleResponse {    
    private String status;
    private String totalResults;
    
    @Getter
    @Setter
    private List<Article> articles = new ArrayList<>(); 
}
