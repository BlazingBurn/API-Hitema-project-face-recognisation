package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception;

import lombok.Data;

@Data
public class UpdateNoIdException extends RuntimeException {

    private String code;

    public UpdateNoIdException(String entity){
        super(String.format("%s no user id procure", entity));
        this.code = entity.toUpperCase() + "-NOT-FOUND";
    }

}
