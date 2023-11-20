package com.jungsuk_2_1.postory.config;

import com.jungsuk_2_1.postory.security.JwtAuthenticationFilter;
import com.jungsuk_2_1.postory.security.OAuthSuccessHandler;
import com.jungsuk_2_1.postory.security.RedirectUrlCookieFilter;
import com.jungsuk_2_1.postory.service.CustomOAuthUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomOAuthUserService oAuthUserService;

    @MockBean
    private OAuthSuccessHandler oAuthSuccessHandler;

    @MockBean
    private RedirectUrlCookieFilter redirectUrlFilter;

    @Test
    public void testSecurityConfiguration() throws Exception {
        // Your test logic here.
        // Use mockMvc to simulate HTTP requests and check responses.
//
//        mockMvc.perform(get("/").contentType());
//                .andExpect(status().isOk())
//                .andExpect(content().string(containString("HelloWorld")));
//

//    @Test
//    void filterChain() {
//        HttpSecurity http = null;
//        http
//                .cors(cors -> corsConfigurationSource()).
//                csrf(csrf -> csrf.disable())
//        ;
//    }

    }

    @Test
    void corsConfigurationSource() {
    }
}
