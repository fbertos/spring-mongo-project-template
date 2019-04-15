package org.fbertos.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.fbertos.persistence.ApplicationRestService;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Before;
import org.junit.Rule;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ApplicationRestService.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application.properties")
public class AuthControllerTest {
	@Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Autowired
    private WebApplicationContext context;
	
    private MockMvc mockMvc;
    
    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }
    
    @Test
    public void testCreateToken() throws Exception {
        this.mockMvc.perform(post("/auth")
        		.param("username", "admin")
        		.param("password", "1234"))
            .andExpect(status().isOk())
            .andDo(document("auth/post",
            		responseFields(fieldWithPath("id").description("Generated Session Token ID"),
            				fieldWithPath("userId").description("User ID"),
            				fieldWithPath("dateTime").description("Creation Date"))
            		));
    }
}