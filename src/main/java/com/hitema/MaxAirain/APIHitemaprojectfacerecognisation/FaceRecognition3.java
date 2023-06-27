package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FaceRecognition3 {

    public static int faceRecognition(String id1, String id2) throws IOException {

        String credentialsToEncode = "acc_fc81f6b73424897" + ":" + "3b921616b281767b67869984281106cc";
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

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonResponse = connectionInput.readLine();

        connectionInput.close();

        System.out.println(jsonResponse);

        int score = 0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            score = jsonNode.get("result").get("score").intValue();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return score;

    }
}

