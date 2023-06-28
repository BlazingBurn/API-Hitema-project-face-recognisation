package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.MaterialFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.UserFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.Material;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class MaterialService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialService.class);
    public static final String COL_NAME="materiel";

    public Material getMaterial(String materialId) throws ExecutionException, InterruptedException {

        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(materialId);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();
        Material material;
        if (documentSnapshot.exists()) {
            LOGGER.info("Material found for : " + materialId);
            material = documentSnapshot.toObject(Material.class);
            return material;
        }
        LOGGER.info("No user found for : " + materialId);
        return null;

    }

    public List<Material> getAllMaterial() {

        Firestore dbFireStore = FirestoreClient.getFirestore();

        CollectionReference collectionRef = dbFireStore.collection(COL_NAME);
        List<Material> materials = new ArrayList<>();

        collectionRef.listDocuments().forEach(documentRef -> {
            try {
                DocumentSnapshot documentSnapshot = documentRef.get().get();
                if (documentSnapshot.exists()) {
                    Material material = documentSnapshot.toObject(Material.class);
                    materials.add(material);
                }
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("An error occur during the process : " + e.getMessage());
            }
        });

        LOGGER.info("Nb materials found : " + materials.size());
        LOGGER.info("Materials found : " + materials);
        return materials;

    }

    public Material updateMaterial(MaterialFormDTO material) throws ExecutionException, InterruptedException {

        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection(COL_NAME).document(material.getMaterialId());
        ApiFuture<DocumentSnapshot> documentSnapshotFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = documentSnapshotFuture.get();

        int quantityT;
        // Check if document exist
        if (documentSnapshot.exists() ) {
            if (material.getQuantityT() == 0) {
                LOGGER.info("Document " + material.getMaterialId() + " exist");
                quantityT = Objects.requireNonNull(documentSnapshot.getLong("quantityT")).intValue();
                material.setQuantityT(quantityT);
            }
        } else {
            // Document not exist
            LOGGER.info("Document " + material.getMaterialId() + " not exist");
            return null;
        }

        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(material);
        LOGGER.info("Material updated : " + writeResultApiFuture.get().getUpdateTime());

        Material materialUpdated = new Material();
        materialUpdated.setMaterielId(material.getMaterialId());
        materialUpdated.setName(material.getName());
        materialUpdated.setQuantityA(material.getQuantityA());
        materialUpdated.setQuantityT(material.getQuantityT());

        return materialUpdated;
    }
}
