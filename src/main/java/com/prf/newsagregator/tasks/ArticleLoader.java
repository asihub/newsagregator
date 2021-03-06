package com.prf.newsagregator.tasks;

import com.prf.newsagregator.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled task for loading new articles from API sources 
 */
@Component
public class ArticleLoader {
    
    private static final Logger log = LoggerFactory.getLogger(ArticleLoader.class);
    
    @Autowired
    ArticleService articleService;
    
    @Scheduled(fixedRate = 300000)
    public void loadArticles() {
        log.info("Loading articles started...");        
        articleService.loadArticles();
        log.info("Loading articles finished");
    }    
}
