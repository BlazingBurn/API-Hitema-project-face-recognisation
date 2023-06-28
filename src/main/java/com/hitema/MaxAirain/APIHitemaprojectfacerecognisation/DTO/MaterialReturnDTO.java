package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialReturnDTO {

    private String materialId;
    private String name;
    private int quantityA;
    private int quantityT;

}
