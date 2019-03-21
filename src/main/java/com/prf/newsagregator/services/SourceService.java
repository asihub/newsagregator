package com.prf.newsagregator.services;

import com.prf.newsagregator.entities.Source;
import com.prf.newsagregator.repositories.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Business logic for Source
 */
@Service
@Transactional
public class SourceService {
    
    @Autowired
    SourceRepository sourceRepository;
    
    public Optional<Source> findById(String id) {
        return sourceRepository.findById(id);
    }
    
    public Source create(Source source) {
        return sourceRepository.save(source);
    }
    
    public Source update(Source source) {
        return sourceRepository.save(source);
    }
    
    public void delete(String id) {
        sourceRepository.deleteById(id);
    }
}
