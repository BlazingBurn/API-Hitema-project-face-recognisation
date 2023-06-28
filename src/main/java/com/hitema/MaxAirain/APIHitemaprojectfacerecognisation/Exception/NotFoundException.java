package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {

    private String code;

    public NotFoundException(String entity, Long id){
        super(String.format("%s with id %d not found",entity, id));
        this.code = entity.toUpperCase() + "-NOT-FOUND";
    }

}
