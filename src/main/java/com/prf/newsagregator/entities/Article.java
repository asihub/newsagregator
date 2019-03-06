package com.prf.newsagregator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Article.IdClass.class)
public class Article implements Serializable {
    
    @ManyToOne
    @JoinColumn(name = "source_id")
    private Source source;
    
    @Column(length = 80)
    private String author;
    
    @Id
    @Column(nullable = false)
    private String title;
    
    private String description;
    private String url;
    private String urlToImage;
    
    @Id
    private LocalDateTime publishedAt;
    
    @Column(nullable = false)
    private String content;
    
    @Data
    static class IdClass implements Serializable {
        private String title;
        private LocalDateTime publishedAt;
    }
}