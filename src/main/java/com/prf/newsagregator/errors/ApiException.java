package com.prf.newsagregator.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiException {    
    private HttpStatusExt status;
    private String message;
    private List<String> errors = new ArrayList<>();    
}
