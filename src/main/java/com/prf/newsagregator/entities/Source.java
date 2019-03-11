package com.prf.newsagregator.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity which describes a source news article comes from
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Source implements Serializable {
    
    @Id
    @Column(unique = true, nullable = false, length = 40)
    private String id;
    
    @Column(length = 80)
    private String name;
    
    @OneToMany
    private Set<Article> articles = new HashSet<>();
    
    @Column(length = 200, nullable = false)
    private String url;
}
