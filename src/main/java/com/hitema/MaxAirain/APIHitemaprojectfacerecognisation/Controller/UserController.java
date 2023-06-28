package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Controller;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.CreatedReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Enums.EntityEnum;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception.NotFoundException;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception.UpdateNoIdException;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Mapper.DTOMapper;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
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

@Tag(name = "User controller")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    DTOMapper dtoMapper;

    /**
     * Add a new user
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Created"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @PostMapping("/create")
    public ResponseEntity<CreatedReturnDTO> createUser(@RequestBody UserFormDTO user) throws ExecutionException, InterruptedException {
        String userId = userService.create(user);
        CreatedReturnDTO dto = new CreatedReturnDTO(userId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Get a user
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/get")
    public ResponseEntity<UserReturnDTO> getUser(@RequestParam String userId) throws ExecutionException, InterruptedException {
        User user = userService.getUser(userId);

        if (user == null) {
            throw new NotFoundException(EntityEnum.USER.toString(), Long.parseLong(userId));
        }

        UserReturnDTO dto = dtoMapper.mapToUserReturnDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Update a user
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @PutMapping("/update")
    public ResponseEntity<UserReturnDTO> updateUser(@RequestBody UserFormDTO user) throws ExecutionException, InterruptedException {

        if (user.getUserId().isEmpty()) {
            throw new UpdateNoIdException(EntityEnum.USER.toString());
        }

        User userUpdated = userService.updateUser(user);

        if (userUpdated == null) {
            throw new NotFoundException(EntityEnum.USER.toString(), Long.parseLong(user.getUserId()));
        }

        LOGGER.info("UserUpdated : " + userUpdated);

        UserReturnDTO dto = dtoMapper.mapToUserReturnDTO(userUpdated);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Delete a user
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @PutMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}
