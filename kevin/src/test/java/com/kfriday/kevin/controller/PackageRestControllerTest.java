package com.kfriday.kevin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kfriday.kevin.common.MockUtil;
import com.kfriday.kevin.dto.ImageDTO;
import com.kfriday.kevin.dto.PackageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql(value = "classpath:db/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PackageRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PackageRestController packageRestController;

    private static final String urlPrefix = "/api/v1";

    @BeforeEach
    public void set_up() {
        mvc = MockMvcBuilders.standaloneSetup(packageRestController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }


    @Test
    public void save_test() throws Exception {

        // given
        List<ImageDTO.RequestDTO> requestImageDTOs = MockUtil.getImageDtoRequestDtos();

        PackageDTO.RequestDTO requestPackageDTO = new PackageDTO.RequestDTO(1L, requestImageDTOs);

        String requestBody = om.writeValueAsString(requestPackageDTO);

        // when
        ResultActions resultActions = mvc.perform(
                post(urlPrefix + "/package")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));

    }

    @Test
    public void get_package() throws Exception {

        // given
        long fakeId = 1L;

        // when
        ResultActions resultActions = mvc.perform(
                get(urlPrefix+ "/package/" + fakeId)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
        resultActions.andExpect(jsonPath("$.data.id").value(1));
        resultActions.andExpect(jsonPath("$.data.trackingNo").value(1));
        resultActions.andExpect(jsonPath("$.data.images[0].filename").value("파일 이름1"));
    }

    @Test
    public void get_packages() throws Exception {

        // given
        int fakeOffset = 0;

        int fakeLimit = 5;
        
        // when
        ResultActions resultActions = mvc.perform(
                get(urlPrefix+ "/packages/" + fakeOffset + "/" + fakeLimit)
        );

        // eye
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // verify
        resultActions.andExpect(jsonPath("$.status").value("success"));
        resultActions.andExpect(jsonPath("$.data.responseDTOS[0].id").value(5));
        resultActions.andExpect(jsonPath("$.data.responseDTOS[1].id").value(4));
    }


}
