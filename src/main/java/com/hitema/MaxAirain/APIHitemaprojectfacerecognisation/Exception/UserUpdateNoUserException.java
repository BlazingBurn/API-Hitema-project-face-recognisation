package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception;

import lombok.Data;

@Data
public class UserUpdateNoUserException extends RuntimeException {

    private String code;

    public UserUpdateNoUserException(String entity){
        super(String.format("%s no user id procure", entity));
        this.code = entity.toUpperCase() + "-NOT-FOUND";
    }

}
