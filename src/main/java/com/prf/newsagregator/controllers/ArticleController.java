package com.prf.newsagregator.controllers;

import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.entities.Source;
import com.prf.newsagregator.services.ArticleService;
import com.prf.newsagregator.services.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller for handling responses to Articles 
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    
    @Autowired
    ArticleService articleService;
    
    @Autowired
    SourceService sourceService;
    
    @GetMapping()
    public Iterable<Article> findRecent() {
        return articleService.findRecent();
    }
    
    /**
     * Retrieves articles in paginated way
     * Recognizes the following params: page, size, sort (e.g. ?page=10&size=20&sort=firstname&sort=lastname,asc)
     * @param pageable
     */
    @GetMapping("/paginated")
    public Page<Article> findPaginated(Pageable pageable) {
        return articleService.findPaginated(pageable);
    }
    
    /**
     * Deletes all articles
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteAll() {
        articleService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * Deletes articles by source
     */
    @DeleteMapping(value = "/delete/{sourceId}")
    public ResponseEntity<?> deleteBySourceId(@PathVariable String sourceId) {
        Optional<Source> source = sourceService.findById(sourceId);
        
        if (!source.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        articleService.deleteBySource(source.get());        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
