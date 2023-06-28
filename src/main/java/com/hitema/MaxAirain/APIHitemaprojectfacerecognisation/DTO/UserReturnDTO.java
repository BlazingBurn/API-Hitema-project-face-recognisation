package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReturnDTO {

    private String userId;
    private String picture;
    private String firstname;
    private String lastname;
    private String role;
    private Date dateinscription;
    private String Reservation;

}
