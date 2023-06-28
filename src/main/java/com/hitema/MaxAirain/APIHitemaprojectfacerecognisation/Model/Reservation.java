package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Reservation {

    private String reservationId;
    private String userId;
    private List<String> materiels;

}
