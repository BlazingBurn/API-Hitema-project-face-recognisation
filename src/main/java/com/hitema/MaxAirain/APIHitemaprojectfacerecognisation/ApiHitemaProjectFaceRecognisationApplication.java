package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation;

import com.google.common.io.Resources;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.FaceRecognitionService;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class ApiHitemaProjectFaceRecognisationApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiHitemaProjectFaceRecognisationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApiHitemaProjectFaceRecognisationApplication.class, args);
	}

}
