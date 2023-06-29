package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Controller;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.*;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Enums.EntityEnum;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception.NotFoundException;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception.UpdateNoIdException;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Mapper.DTOMapper;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.Reservation;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.ReservationService;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Tag(name = "Reservation controller")
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    ReservationService reservationService;

    @Autowired
    DTOMapper dtoMapper;

    /**
     * Add a new reservation
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @PostMapping("/create")
    public ResponseEntity<CreatedReturnDTO> createReservation(@RequestBody ReservationFormDTO reservation) throws ExecutionException, InterruptedException {
        String reservationId = reservationService.create(reservation);
        CreatedReturnDTO dto = new CreatedReturnDTO(reservationId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Get a reservation
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/get")
    public ResponseEntity<ReservationReturnDTO> getUser(@RequestParam String userId) throws ExecutionException, InterruptedException {
        Reservation reservation = reservationService.getReservation(userId);

        if (reservation == null) {
            throw new NotFoundException(EntityEnum.RESERVATION.toString(), Long.parseLong(userId));
        }

        ReservationReturnDTO dto = dtoMapper.mapToReservationReturnDTO(reservation);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Update a reservation
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @PostMapping("/update")
    public ResponseEntity<ReservationReturnDTO> updateReservation(@RequestBody ReservationFormUpdateDTO reservation) throws ExecutionException, InterruptedException {

        if (reservation.getReservationId().isEmpty()) {
            throw new UpdateNoIdException(EntityEnum.RESERVATION.toString());
        }

        Reservation reservationUpdated = reservationService.updateReservation(reservation);

        if (reservationUpdated == null) {
            throw new NotFoundException(EntityEnum.USER.toString(), Long.parseLong(reservationUpdated.getReservationId()));
        }

        LOGGER.info("ReservationUpdated : " + reservationUpdated);

        ReservationReturnDTO dto = dtoMapper.mapToReservationReturnDTO(reservationUpdated);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
