package com.prf.newsagregator.repositories;

import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.entities.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Set<Article> findFirst10ByOrderByPublishedAtDesc();
    void deleteBySource(Source source);
}
