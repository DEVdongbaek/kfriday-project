package com.kfriday.kevin.exception.customException;

public class DoesNotExistPackageException extends RuntimeException {

    public DoesNotExistPackageException(String errorMessage){
        super(errorMessage);
    }

}
