package com.prf.newsagregator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Core entity which describes an article for a piece of news
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Article.IdClass.class)
public class Article implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "source_id")
    private Source source;
    
    @Column(length = 120)
    private String author;
    
    @Id
    @Column(nullable = false)
    private String title;
    
    @Column(length = 300)
    private String description;
    
    @Column(length = 300)
    private String url;
    private String urlToImage;
    
    @Id
    private LocalDateTime publishedAt;
    
    @Column(length = 300)
    private String content;
    
    @Column
    @CreationTimestamp
    private LocalDateTime createDateTime;    
    
    @Data
    static class IdClass implements Serializable {
        private String title;
        private LocalDateTime publishedAt;
    }
}
