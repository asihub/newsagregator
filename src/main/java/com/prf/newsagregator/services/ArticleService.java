package com.prf.newsagregator.services;

import com.prf.newsagregator.dtos.ArticleResponse;
import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.repositories.ArticleRepository;
import com.prf.newsagregator.repositories.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ArticleService {
    
    private static final String URL_BBC = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=aa1eed5864ed4b09be2bae237cfd1db9";
    
    @Autowired
    RestTemplate client;
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Autowired
    SourceRepository sourceRepository;
    
    public void loadArticles() {
        ResponseEntity<ArticleResponse> articleResponse = client.getForEntity(URL_BBC, ArticleResponse.class);
        
        List<Article> articles = articleResponse.getBody().getArticles();
        articleRepository.saveAll(articles);
    }
    
}
