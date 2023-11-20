package com.jungsuk_2_1.postory.security;

import com.jungsuk_2_1.postory.service.CustomOAuthUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OAuth2CallbackTest {

    @Autowired
    private Environment env;

    @InjectMocks
    private CustomOAuthUserService customOAuthUserService;

    @MockBean
    private DefaultOAuth2UserService defaultOAuth2UserService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockCustomOAuth2User
    public void testOAuth2Callback() throws Exception {
        String clientId = env.getProperty("spring.security.oauth2.client.registration.google.client-id");
        String clientSecret = env.getProperty("spring.security.oauth2.client.registration.google.client-secret");

        ClientRegistration existingRegistration = ClientRegistration.withRegistrationId("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        ClientRegistration.Builder builder = ClientRegistration.withClientRegistration(existingRegistration);

        OAuth2UserRequest userRequest = new OAuth2UserRequest(
                builder.build()
                ,
                new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "tokenvalue",null,null)
        );

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "109894178962707348442");
        attributes.put("name", "You’re Loved");
        attributes.put("given_name", "You’re");
        attributes.put("family_name", "Loved");
        attributes.put("picture", "https://lh3.googleusercontent.com/a/ACg8ocKJCycbTvk2vO7EIIJHq1jQH3YKJGk_ocO_d9VtvzcE=s96-c");
        attributes.put("email", "rightlightfg@gmail.com");
        attributes.put("email_verified", true);
        attributes.put("locale", "ko");

        ApplicationOAuth2User oAuth2User = ApplicationOAuth2User.builder().attributes(attributes
        ).authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))).build();

        customOAuthUserService.setOAuth2User(oAuth2User);

        OAuth2User result = customOAuthUserService.loadUser(userRequest);

        assertEquals("You’re Loved", result.getAttribute("name"));

    }
}
