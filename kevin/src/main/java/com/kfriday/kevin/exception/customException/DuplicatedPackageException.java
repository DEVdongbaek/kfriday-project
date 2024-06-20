package com.kfriday.kevin.exception.customException;

public class DuplicatedPackageException extends RuntimeException {

    public DuplicatedPackageException(String errorMessage){
        super(errorMessage);
    }

}
