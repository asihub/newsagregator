package com.prf.newsagregator.controllers;

import com.prf.newsagregator.entities.Source;
import com.prf.newsagregator.services.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Controller with CRUD operation for Source
 */
@RestController
@RequestMapping("/api/source")
public class SourceController {
    
    @Autowired
    SourceService sourceService;
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {    
        Optional<Source> source = sourceService.findById(id);
                
        if (!source.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       
        return new ResponseEntity<>(source.get(), HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Source source) {
        Optional<Source> sourceFromDb = sourceService.findById(source.getId());
        
        if (sourceFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
        return new ResponseEntity<>(sourceService.create(source), HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody Source source) {
        Optional<Source> sourceFromDb = sourceService.findById(source.getId());
        
        if (!sourceFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }    
    
        return new ResponseEntity<>(sourceService.update(source), HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Optional<Source> sourceFromDb = sourceService.findById(id);
        
        if (!sourceFromDb.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }      
    
        sourceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
