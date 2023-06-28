package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    public static final String COL_NAME="user";

    public String create(UserFormDTO user) throws ExecutionException, InterruptedException {

        LocalDateTime timestamp = LocalDateTime.now();
        user.setDateinscription(Date.from(timestamp.toInstant(ZoneOffset.UTC)));

        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(); // Generate a new document reference without specifying the document ID
        String userId = documentReference.getId(); // Get the generated document ID
        user.setUserId(userId); // Set the document ID as the userId field in the UserFormDTO object

        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(user);

        LOGGER.info("Added document with ID: " + userId);
        return userId;
    }

    public User getUser(String userId) throws ExecutionException, InterruptedException {

        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(userId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();
        User user;
        if (documentSnapshot.exists()) {
            LOGGER.info("User found for : " + userId);
            user = documentSnapshot.toObject(User.class);
            return user;
        }
        LOGGER.info("No user found for : " + userId);
        return null;

    }

    public User updateUser(UserFormDTO user) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();

        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(user.getUserId());
        ApiFuture<DocumentSnapshot> documentSnapshotFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotFuture.get();

        Date dateinscription;
        String picture;
        // Check if document exist
        if (documentSnapshot.exists()) {
            LOGGER.info("Document " + user.getUserId() + " exist");
            dateinscription = documentSnapshot.getDate("dateinscription");
            picture = documentSnapshot.getString("picture");
            user.setDateinscription(dateinscription);
        } else {
            // Document not exist
            LOGGER.info("Document " + user.getUserId() + " not exist");
            return null;
        }

        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(user);
        LOGGER.info("User updated : " + writeResultApiFuture.get().getUpdateTime());

        User userUpdated = new User();
        userUpdated.setUserId(user.getUserId());
        userUpdated.setPicture(picture);
        userUpdated.setRole(user.getRole());
        userUpdated.setFirstname(user.getFirstname());
        userUpdated.setLastname(user.getLastname());
        userUpdated.setDateinscription(dateinscription);

        return userUpdated;
    }

    public void deleteUser(String userId) {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResultApiFuture = dbFireStore.collection(COL_NAME).document(userId).delete();
        LOGGER.info("User with ID : " + userId + ", deleted");
    }
}
