package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationFormDTO {

    @NotNull
    private String userId;

    private String reservationId;

    @NotNull
    private List<String> materiels;

}
