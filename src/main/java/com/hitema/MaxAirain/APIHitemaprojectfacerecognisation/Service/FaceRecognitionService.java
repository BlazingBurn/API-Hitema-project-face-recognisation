package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class FaceRecognitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceRecognitionService.class);

    @Value("${spring.secret.immaga}")
    private String credentialsToEncode;

    public int faceRecognition(String id1, String id2) throws IOException {

        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

        String endpoint_url = "https://api.imagga.com/v2/faces/similarity/";

        // These are example face IDs, they won't work. Generate your own using the /faces/detections endpoint.
        String face_id = id1;
        String second_face_id = id2;

        String url = endpoint_url + "?face_id=" + face_id + "&second_face_id=" + second_face_id;
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        connection.setRequestProperty("Authorization", "Basic " + basicAuth);

        int responseCode = connection.getResponseCode();

        LOGGER.info("\nSending 'GET' request to URL : " + url);
        LOGGER.info("Response Code : " + responseCode);

        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonResponse = connectionInput.readLine();

        connectionInput.close();

        LOGGER.info(jsonResponse);

        int score = 0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            score = jsonNode.get("result").get("score").intValue();

        } catch (Exception e) {
            LOGGER.error("FaceRecognition failed during json parsing => get score from json response failed");
            e.printStackTrace();
        }

        return score;

    }
}

