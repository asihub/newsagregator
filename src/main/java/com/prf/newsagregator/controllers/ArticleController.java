package com.prf.newsagregator.controllers;

import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling responses to Articles 
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    
    @Autowired
    ArticleService articleService;
    
    @GetMapping()
    public Iterable<Article> findRecent() {
        return articleService.findRecent();
    }
    
    /**
     * Method to retrieve articles in paginated way
     * Recognizes the following params: page, size, sort (e.g. ?page=10&size=20&sort=firstname&sort=lastname,asc)
     * @param pageable
     */
    @GetMapping("/paginated")
    public Page<Article> findPaginated(Pageable pageable) {
        return articleService.findPaginated(pageable);
    }
}
