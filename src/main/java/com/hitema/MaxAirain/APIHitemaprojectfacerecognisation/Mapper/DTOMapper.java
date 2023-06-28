package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Mapper;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.MaterialListReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.MaterialReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.ReservationReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.Material;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.Reservation;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOMapper {

    public UserReturnDTO mapToUserReturnDTO(User user) {
        return new UserReturnDTO(//
                user.getUserId(), //
                user.getPicture(), //
                user.getFirstname(), //
                user.getLastname(), //
                user.getRole(), //
                user.getDateinscription(), //
                user.getReservationId()
        );
    }

    public MaterialReturnDTO mapToMaterialReturnDTO(Material material) {
        return new MaterialReturnDTO(//
                material.getMaterielId(), //
                material.getName(), //
                material.getQuantityA(), //
                material.getQuantityT()
        );
    }

    public MaterialListReturnDTO mapToMaterialListReturnDTO(List<Material> materials) {
        return new MaterialListReturnDTO(
                materials.stream().map(this::mapToMaterialReturnDTO).collect(Collectors.toList()));
    }

    public ReservationReturnDTO mapToReservationReturnDTO(Reservation reservation) {
        return new ReservationReturnDTO(//
                reservation.getReservationId(), //
                reservation.getUserId(), //
                reservation.getMateriels()
        );
    }

}
