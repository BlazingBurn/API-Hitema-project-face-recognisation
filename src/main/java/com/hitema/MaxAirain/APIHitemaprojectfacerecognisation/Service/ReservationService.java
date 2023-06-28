package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.MaterialFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.ReservationFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.Material;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);
    public static final String COL_NAME="reservation";
    public static final String USER_COL_NAME="user";

    public String create(ReservationFormDTO reservation) throws ExecutionException, InterruptedException {

        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(); // Generate a new document reference without specifying the document ID
        String reservationId = documentReference.getId(); // Get the generated document ID
        reservation.setReservationId(reservationId); // Set the document ID as the userId field in the UserFormDTO object

        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(reservation);

        LOGGER.info("Added document with ID: " + reservationId);
        return reservationId;
    }

    public Reservation getReservation(String userId) throws ExecutionException, InterruptedException {

        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReferenceUser = dbFireStore.collection(USER_COL_NAME).document(userId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFutureUser = documentReferenceUser.get();
        DocumentSnapshot documentSnapshotUser = documentSnapshotApiFutureUser.get();

        String reservationId;
        if (documentSnapshotUser.exists()) {
            LOGGER.info("Reservation found for : " + userId);
            reservationId = documentSnapshotUser.getString("reservationId");
        } else {
            LOGGER.info("No document found for : " + userId);
            return null;
        }

        assert reservationId != null;
        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(reservationId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();
        Reservation reservation;
        if (documentSnapshot.exists()) {
            LOGGER.info("Reservation found for : " + reservationId);
            reservation = documentSnapshot.toObject(Reservation.class);
            return reservation;
        }
        LOGGER.info("No reservation found for : " + reservationId);
        return null;

    }

    public Reservation updateReservation(ReservationFormDTO reservation) throws ExecutionException, InterruptedException {

        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(reservation.getReservationId());
        ApiFuture<DocumentSnapshot> documentSnapshotFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotFuture.get();

        // Check if document exist
        if (!documentSnapshot.exists()) {
            // Document not exist
            LOGGER.info("Document Reservation : " + reservation.getReservationId() + " not exist");
            return null;
        }

        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(reservation);
        LOGGER.info("Reservation updated : " + writeResultApiFuture.get().getUpdateTime());

        Reservation reservationUpdated = new Reservation();
        reservationUpdated.setReservationId(reservation.getReservationId());
        reservationUpdated.setUserId(reservation.getUserId());
        reservationUpdated.setMateriels(reservation.getMateriels());

        return reservationUpdated;
    }

}
