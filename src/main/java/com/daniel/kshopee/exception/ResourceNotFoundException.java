package com.daniel.kshopee.exception;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private long fieldValue;

    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue){
        super(String.format("'%s with %s : %s is not found'",resourceName,fieldName,fieldValue));
        this.resourceName =resourceName;
        this.fieldName =fieldName;
        this.fieldValue=fieldValue;
    }
}
