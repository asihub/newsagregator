package com.prf.newsagregator.repositories;

import com.prf.newsagregator.dtos.RecordsCountResponse;
import com.prf.newsagregator.entities.Article;
import com.prf.newsagregator.entities.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Set<Article> findFirst10ByOrderByPublishedAtDesc();
    
    void deleteBySource(Source source);
    
    @Query("select count(a) from Article a where a.createDateTime >= :createDateTime")
    Integer findCountByCreateDateTimeAfter(@Param("createDateTime") LocalDateTime createDateTime);
    
    @Query(value = "select source_id as fieldName, count(*) as recordsCount from article group by source_id", nativeQuery = true)
    Collection<RecordsCountResponse> findCountBySource();
}
