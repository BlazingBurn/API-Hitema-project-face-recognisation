package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationReturnDTO {

    private String reservationId;
    private String userId;
    private List<String> materiels;

}
