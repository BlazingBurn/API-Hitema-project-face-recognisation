package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FaceFormDTO {

    @NotNull
    private String picture;

}
