package com.prf.newsagregator.errors;

public enum HttpStatusExt {

    CONSTRAINT_VIOLATION(460,"Constraint violation");
    
    private final int value;
    private final String reasonPhrase;
    
    HttpStatusExt(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }
}
