package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Controller;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.MaterialFormDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.MaterialListReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.DTO.MaterialReturnDTO;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Enums.EntityEnum;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception.NotFoundException;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Exception.UpdateNoIdException;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Mapper.DTOMapper;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model.Material;
import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.MaterialService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Tag(name = "Material controller")
@RestController
@RequestMapping("/api/v1/materials")
public class MaterialController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    MaterialService materialService;

    @Autowired
    DTOMapper dtoMapper;

    /**
     * Get a material
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/get")
    public ResponseEntity<MaterialReturnDTO> getMaterial(@RequestParam String materialId) throws ExecutionException, InterruptedException {
        Material material = materialService.getMaterial(materialId);

        if (material == null) {
            throw new NotFoundException(EntityEnum.MATERIAL.toString(), Long.parseLong(materialId));
        }

        MaterialReturnDTO dto = dtoMapper.mapToMaterialReturnDTO(material);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Get all materials
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @GetMapping("/getAll")
    public ResponseEntity<MaterialListReturnDTO> getAllMaterial() throws ExecutionException, InterruptedException {
        List<Material> materials = materialService.getAllMaterial();

        MaterialListReturnDTO dto = dtoMapper.mapToMaterialListReturnDTO(materials);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Update a user
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "20O", description = "OK"), @ApiResponse(responseCode = "404", description = "Not Found") })
    @PutMapping("/update")
    public ResponseEntity<MaterialReturnDTO> updateMaterial(@RequestBody MaterialFormDTO materialFormDTO) throws ExecutionException, InterruptedException {

        if (materialFormDTO.getMaterialId().isEmpty()) {
            throw new UpdateNoIdException(EntityEnum.MATERIAL.toString());
        }

        LOGGER.info("Material (before update) : " + materialFormDTO);

        Material materialUpdated = materialService.updateMaterial(materialFormDTO);

        if (materialUpdated == null) {
            throw new NotFoundException(EntityEnum.USER.toString(), Long.parseLong(materialUpdated.getMaterielId()));
        }

        LOGGER.info("MaterialUpdated : " + materialUpdated.toString());

        MaterialReturnDTO dto = dtoMapper.mapToMaterialReturnDTO(materialUpdated);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
