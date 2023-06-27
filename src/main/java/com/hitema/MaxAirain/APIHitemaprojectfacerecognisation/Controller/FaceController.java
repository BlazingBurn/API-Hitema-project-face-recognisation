package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Controller;

import com.google.common.io.Resources;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.FaceRecognitionService;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "Face recognition")
@RestController
@RequestMapping("/api/v1/face-recognition")
public class FaceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceController.class);

    @Autowired
    FaceRecognitionService faceRecognitionService;

    @Autowired
    ImageService imageService;


//    public void a() throws IOException {
//        // Test base64
//        URL url = Resources.getResource("imageTestFront.txt");
//        String data = Resources.toString(url, StandardCharsets.UTF_8);
//
//        InputStream face_compared = imageService.Base64ToInputStream(data);
//
//        String id1 = imageService.getFaceIdAPI("face_compared", face_compared);
//
//        // Test image
//        File fileToUpload = new File("src/main/resources/image5Test.jpg");
//        InputStream face_comparing = new FileInputStream(fileToUpload);
//        String id2 = imageService.getFaceIdAPI("face_comparing", face_comparing);
//        int score = faceRecognitionService.faceRecognition(id1, id2);
//        LOGGER.info(String.valueOf(score));
//    }

    /**
     * Compares the face sent by the front to all faces in the DB and returns a boolean based on the compatibility score
     */
    @Operation(summary = "Send a boolean if the score based on the compatibility is high")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    @GetMapping
    public ResponseEntity<UserReturnDTO> faceRecognitionProcessus(@RequestPart(value = "face", required = true) MultipartFile file) {

        try {
            LOGGER.info("FaceController - Start the processus to find a face and connect the user");

            // Get transferred image data
            InputStream face_compared = file.getInputStream();

            // Get all images in the DB and create a inputstream list of each images
            //TODO passer par le userService pour get les images
//            List<InputStream> inputStreamsFacesDB = TODO;

            String id1 = imageService.getFaceIdAPI("face_compared", face_compared);

//            TODO for (InputStream inputstreamface: inputStreamsFacesDB) {
//                LOGGER.info("FaceController - Start the comparaison");
//                // face groupings
//                //
//                String id2 = imageService.getFaceIdAPI("face_comparing", face_comparing);
//
//            }

//            int score = faceRecognitionService.faceRecognition(id1, id2);
//            LOGGER.info("Le meilleur score obtenu est " + score);

//            UserReturnDTO dto = new UserReturnDTO(isSimilar, score);
            UserReturnDTO dto = new UserReturnDTO();
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
