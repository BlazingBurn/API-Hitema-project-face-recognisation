package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.ServiceTest;

import com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MaterialServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MaterialService materialService;

    /**
     * Unit Test for the method getAllMaterial
     *
     *  Part => Sucess
     *
     */
    // Check if it's works well
    @Test
    void getAllMaterial_sucess() throws Exception {
        mockMvc.perform(get("/api/v1/materials/getAll")
                        .contentType("application/json; charset=utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.materials", hasSize(14)));
    }

}
