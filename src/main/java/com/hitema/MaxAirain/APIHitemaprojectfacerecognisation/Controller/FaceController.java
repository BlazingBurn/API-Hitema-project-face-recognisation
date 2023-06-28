package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Controller;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.FaceFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserFaceReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.FaceRecognitionService;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.ImageService;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Tag(name = "Face recognition")
@RestController
@RequestMapping("/api/v1/face-recognition")
public class FaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceController.class);

    @Autowired
    FaceRecognitionService faceRecognitionService;

    @Autowired
    ImageService imageService;

    @Autowired
    UserService userService;

    /**
     * Compares the face sent by the front to all faces in the DB and returns a boolean based on the compatibility score
     */
    @Operation(summary = "Send a boolean if the score based on the compatibility is high")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    @GetMapping
    public ResponseEntity<UserFaceReturnDTO> faceRecognitionProcessus(@RequestBody FaceFormDTO face) {

        try {
            LOGGER.info("FaceController - Start the processus to find a face and connect the user");

            // Get transferred image data
            InputStream face_compared = imageService.Base64ToInputStream(face.getPicture());

            // Get all images in the DB and create a inputstream list of each images
            List<User> users = userService.getAllUser();
            List<InputStream> inputStreamsFacesDB = new ArrayList<>();

            for (User user: users) {
                inputStreamsFacesDB.add(imageService.Base64ToInputStream(user.getPicture()));
            }

            String idFace_compared = imageService.getFaceIdAPI("face_compared", face_compared);

            String idFace_db_list;
            Map<String, Double> score = new HashMap<>();
            for (int i = 0; i < users.size(); i++) {
                LOGGER.info("FaceController - Start the comparaison");

                idFace_db_list = imageService.getFaceIdAPI("face_comparing", inputStreamsFacesDB.get(i));
                score.put(users.get(i).getUserId(), faceRecognitionService.faceRecognition(idFace_compared, idFace_db_list));

            }

            List<Map.Entry<String, Double>> sortedList = new ArrayList<>(score.entrySet());
            // Sort the list based on the highest value
            sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            // Get best score
            Map.Entry<String, Double> bestScore = sortedList.get(0);

            LOGGER.info("Le meilleur score obtenu est " + bestScore.getValue() + ", pour le user : " + bestScore.getKey());

            boolean isSimilar = true;
            UserFaceReturnDTO dto;

            // If score > 80 recognition is good
            if (bestScore.getValue() > 80) {
                dto = new UserFaceReturnDTO(isSimilar, bestScore.getValue(), bestScore.getKey());
            } else {
                dto = new UserFaceReturnDTO(!isSimilar, bestScore.getValue(), null);
            }

            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (InterruptedException | ExecutionException | IOException e) {
            LOGGER.error("An error occur during the process : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
