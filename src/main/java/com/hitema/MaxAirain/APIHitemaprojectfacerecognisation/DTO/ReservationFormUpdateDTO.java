package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReservationFormUpdateDTO {

    private String userId;

    @NotNull
    private String reservationId;

    @NotNull
    private List<String> materiels;

}
