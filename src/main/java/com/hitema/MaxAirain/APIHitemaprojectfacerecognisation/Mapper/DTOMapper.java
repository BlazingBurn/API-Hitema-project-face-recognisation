package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Mapper;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import org.springframework.stereotype.Component;

@Component
public class DTOMapper {

    public UserReturnDTO mapToUserReturnDTO(User user) {
        return new UserReturnDTO(//
                user.getUserId(), //
                user.getPicture(), //
                user.getFirstname(), //
                user.getLastname(), //
                user.getRole(), //
                user.getDateinscription()
        );
    }

}
