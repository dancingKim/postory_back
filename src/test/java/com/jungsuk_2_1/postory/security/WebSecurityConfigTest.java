package com.jungsuk_2_1.postory.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOAuth2LoginSuccess() throws Exception {
        mockMvc.perform(get("/auth/authorize")) // Replace with your protected URL
                .andExpect(status().is(302));
    }

    @Test
    public void testJwtAuthentication() throws Exception {
        String token = "your-real-or-mocked-jwt-token";
        mockMvc.perform(get("/auth").header("Authorization", "Bearer " + token)) // Replace with your JWT protected URL
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessDenied() throws Exception {
        mockMvc.perform(get("/auth")) // Replace with your protected URL
                .andExpect(status().isUnauthorized());
    }
}
