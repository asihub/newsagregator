package com.prf.newsagregator.services;

import com.prf.newsagregator.dtos.ArticleResponse;
import com.prf.newsagregator.dtos.RecordsCountResponse;
import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.entities.Source;
import com.prf.newsagregator.repositories.ArticleRepository;
import com.prf.newsagregator.repositories.SourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Business logic for articles
 */
@Service
public class ArticleService {
    
    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);
    
    @Autowired
    RestTemplate client;
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Autowired
    SourceRepository sourceRepository;
    
    /**
     * Loads articles for a single source
     * @param source of news articles
     */
    @Transactional
    public void loadArticlesBy(Source source) {
        try {
            log.info("Loading articles for the source: [id={}, name={}]", source.getId(), source.getName());
            ResponseEntity<ArticleResponse> articleResponse = client.getForEntity(source.getUrl(), ArticleResponse.class);
            List<Article> articles = articleResponse.getBody().getArticles();
            articleRepository.saveAll(articles);
        } catch (Exception e) {
            log.error("Loading articles has been failed for [id={}, name={}] due to the following error:",
                    source.getId(), source.getName());
            e.printStackTrace();
        }
    }
    
    /**
     * Calls loadArticlesBy(Source source) for each news API resource
     */
    public void loadArticles() {
        log.info("Searching all articles");
        sourceRepository.findAll().forEach(this::loadArticlesBy);
    }
    
    /**
     * Returns 10 most recent articles from all sources
     */
    public Iterable<Article> findRecent() {
        log.info("Searching last 10 recent articles");
        return articleRepository.findFirst10ByOrderByPublishedAtDesc();
    }
    
    public Page<Article> findPaginated(Pageable pageable) {
        log.info("Searching specific page with articles [page = $s]", pageable.getPageNumber());
        return articleRepository.findAll(pageable);
    }
    
    @Transactional
    public void deleteAll() {
        log.info("Deleting all articles");
        articleRepository.deleteAll();
    }
    
    @Transactional
    public void deleteBySource(Source source) {
        log.info("Deleting article for a source [source = %s]", source.getId());
        articleRepository.deleteBySource(source);
    }
    
    public Integer findRecentCount(int recentSeconds) {
        log.info("Gathering statistic about recently loaded articles [recentSeconds = %s]", recentSeconds);
        return articleRepository.findCountByCreateDateTimeAfter(LocalDateTime.now().minusSeconds(recentSeconds));
    }    
    
    public Collection<RecordsCountResponse> findCountBySource() {
        log.info("Gathering statistic about count of loaded articles per source");
        return articleRepository.findCountBySource();
    }
}
