package com.prf.newsagregator.errors;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiException handleConstraintViolation(ConstraintViolationException ex, HttpServletResponse response) {
        
        ApiException errorResponse = new ApiException();
        
        if (!CollectionUtils.isEmpty(ex.getConstraintViolations())) {
            ex.getConstraintViolations()
                    .forEach(cv -> errorResponse.getErrors()
                            .add(cv.getMessage() + " " + cv.getPropertyPath() + cv.getInvalidValue()));
        }
        
        errorResponse.setStatus(HttpStatusExt.CONSTRAINT_VIOLATION);
        errorResponse.setMessage("Constraint error " + ex.getLocalizedMessage());        
        
        return errorResponse;
    }
}
