package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {

    private String userId;
    private String firstname;
    private String lastname;
    private String picture;
    private String role;
    private Date dateinscription;
    private String reservationId;

}
