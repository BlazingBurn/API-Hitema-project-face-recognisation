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

//	public void demo() throws IOException {
//		URL url = Resources.getResource("imageTestFront.txt");
//		String data = Resources.toString(url, StandardCharsets.UTF_8);
//		// Conversion base64 to image
//		String base64Image = data.split(",")[1];
//		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
//		BufferedImage image2BufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
//		// Write the image to a file
//		File outputFile = new File("image1.jpeg");
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ImageIO.write(image2BufferedImage, "jpeg", outputStream);
//		InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//		String id1 = ImageService.getImageIdAPI(outputFile, inputStream);
//
//		File fileToUpload = new File("src/main/resources/image5Test.jpg");
//		inputStream = new FileInputStream(fileToUpload);
//		String id2 = ImageService.getImageIdAPI(fileToUpload, inputStream);
//		int score = FaceRecognitionService.faceRecognition(id1, id2);
//		LOGGER.info(String.valueOf(score));
//	}

}
