package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFaceReturnDTO {

    private boolean isSimilar;
    private double score;
    private String userId;

}
