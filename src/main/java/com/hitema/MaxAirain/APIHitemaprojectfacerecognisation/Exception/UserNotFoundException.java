package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {

    private String code;

    public UserNotFoundException(String entity, Long id){
        super(String.format("%s with id %d not found",entity, id));
        this.code = entity.toUpperCase() + "-NOT-FOUND";
    }

}
