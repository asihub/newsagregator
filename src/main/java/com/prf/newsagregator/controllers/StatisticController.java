package com.prf.newsagregator.controllers;

import com.prf.newsagregator.dtos.RecordsCountResponse;
import com.prf.newsagregator.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

/**
 * Controller for providing statistics info about articles
 */
@RestController
@RequestMapping("/api/statistics")
public class StatisticController {
    
    private static final int RECENT_SECONDS = 60;
    
    @Autowired
    ArticleService articleService;
    
    /**
     * @return count of articles loaded for last @param recentSecond
     * if @param recentSecond is not received then recentSecond = 60s
     */
    @GetMapping(value = {"/recentCount/{recentSecondsOpt}", "/recentCount"})
    public ResponseEntity<?> findRecentCount(@PathVariable Optional<Integer> recentSecondsOpt) {
        int recentSeconds = RECENT_SECONDS;
        
        if (recentSecondsOpt.isPresent()) {
            recentSeconds = recentSecondsOpt.get();
        }
    
        return new ResponseEntity<>(articleService.findRecentCount(recentSeconds), HttpStatus.OK);
    }
    
    /**
     * @return count of articles per source
     */
    @GetMapping(value = "/countBySource")
    public ResponseEntity<?> findCountBySource() {
        Collection<RecordsCountResponse> countBySource = articleService.findCountBySource();
        
        if (CollectionUtils.isEmpty(countBySource)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(countBySource, HttpStatus.OK);
    }
    
}
