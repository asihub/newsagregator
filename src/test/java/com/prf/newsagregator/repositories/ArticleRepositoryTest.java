package com.prf.newsagregator.repositories;

import com.prf.newsagregator.common.BaseRepositoryTest;
import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.entities.Source;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
                "classpath:sql/create_articles.sql"
        }),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
                "classpath:sql/delete_articles.sql"
        })
})
public class ArticleRepositoryTest extends BaseRepositoryTest {
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Test
    public void testFindFirst10ByOrderByPublishedAtDesc() throws Exception {
        Set<Article> recentArticles = articleRepository.findFirst10ByOrderByPublishedAtDesc();
        
        List<Article> allArticles = articleRepository.findAll();
        allArticles.sort(new publishedAtComparator());
        
        for (int i = 0; i < allArticles.size() - 10; i++) {
            if (recentArticles.contains(allArticles.get(i))) {
                throw new Exception();
            }
        }
    }
    
    @Test
    public void testDeleteBySource() {
        final Source source = new Source("abc-news", "ABC News", null,
                "https://newsapi.org/v2/everything?sources=espn&apiKey=aa1eed5864ed4b09be2bae237cfd1db9");
        
        Assert.assertEquals(20, articleRepository.findAll().size());
        
        articleRepository.deleteBySource(source);
        
        Assert.assertEquals(3, articleRepository.findAll().size());
    }
    
    @Test
    public void testFindCountByCreateDateTimeAfter() {
        final LocalDateTime timeAfter = LocalDateTime.of(2019, Month.MARCH, 19, 10, 55, 1);
        
        Assert.assertEquals(Integer.valueOf(10), articleRepository.findCountByCreateDateTimeAfter(timeAfter));
    }
    
    @Test
    public void testFindCountBySource() {
        Assert.assertEquals(2, articleRepository.findCountBySource().size());
    }
    
    class publishedAtComparator implements Comparator<Article> {
        @Override
        public int compare(Article o1, Article o2) {
            return o1.getPublishedAt().compareTo(o2.getPublishedAt());
        }
    }
    
}


