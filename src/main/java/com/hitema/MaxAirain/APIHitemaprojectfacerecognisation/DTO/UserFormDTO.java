package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserFormDTO {

    private String userId;

    @NotNull
    private String picture;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String role;

    private Date dateinscription;

    private String reservationId;

}
