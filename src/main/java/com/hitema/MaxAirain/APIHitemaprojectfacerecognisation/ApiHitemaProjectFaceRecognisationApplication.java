package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@SpringBootApplication
public class ApiHitemaProjectFaceRecognisationApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(ApiHitemaProjectFaceRecognisationApplication.class, args);

        URL url = Resources.getResource("imageTestFront.txt");
        String data = Resources.toString(url, StandardCharsets.UTF_8);
        // Conversion base64 to image
        String base64Image = data.split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        BufferedImage image2BufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
		// Write the image to a file
		File outputFile = new File("image1.jpeg");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image2BufferedImage, "jpeg", outputStream);
		InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		String id1 = ImageUpload.getImageIdAPI(outputFile, inputStream);

		File fileToUpload = new File("src/main/resources/image5Test.jpg");
		inputStream = new FileInputStream(fileToUpload);
		String id2 = ImageUpload.getImageIdAPI(fileToUpload, inputStream);
		int score = FaceRecognition3.faceRecognition(id1, id2);
		System.out.println(score);
	}

}
