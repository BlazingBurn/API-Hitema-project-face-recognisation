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
        LOGGER.info("User found for : " + userId);
        if (documentSnapshot.exists()) {
            user = documentSnapshot.toObject(User.class);
                    return user;
        }
        LOGGER.info("No user found for : " + userId);
        return null;

    }

    public User updateUser(UserFormDTO user) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection(COL_NAME).document(user.getUserId()).set(user);
        LOGGER.info("User updated : " + collectionApiFuture.get().getUpdateTime());

        User userUpdated = new User();
        userUpdated.setRole(user.getRole());
        userUpdated.setFirstname(user.getFirstname());
        userUpdated.setLastname(user.getLastname());

        return userUpdated;
    }

    public void deleteUser(String userId) {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResultApiFuture = dbFireStore.collection(COL_NAME).document(userId).delete();
        LOGGER.info("User with ID : " + userId + ", deleted");
    }
}
