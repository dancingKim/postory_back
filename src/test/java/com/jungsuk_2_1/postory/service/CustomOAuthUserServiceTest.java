package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.UserDao;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomOAuthUserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private CustomOAuthUserService customOAuthUserService;

    @Mock
    private OAuth2UserRequest oAuth2UserRequest;

    @Mock
    private OAuth2User oAuth2User;


    @Test
    void loadUser() {

        UserDto mockUser = mock(UserDto.class);
        when(userDao.findByUserEmail(anyString())).thenReturn(mockUser);

        ClientRegistration mockClientRegistration = mock(ClientRegistration.class);
        when(mockClientRegistration.getRegistrationId()).thenReturn("google");
        when(oAuth2UserRequest.getClientRegistration()).thenReturn(mockClientRegistration);

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub","111572050751332051605");
        attributes.put("name","I am loved");
        attributes.put("given_name","I am");
        attributes.put("family_name","loved");
        attributes.put("picture","https://lh3.googleusercontent.com/a/AAcHTtde0mKcxuRdJ-rpG_5s0_x-fjClgGJXh-pKybLBJP0A=s96-c");
        attributes.put("email","iamloved5959@gmail.com");
        attributes.put("email-verified",true);
        attributes.put("locale","ko");

        when(oAuth2User.getAttributes()).thenReturn(attributes);

OAuth2User result = customOAuthUserService.loadUser(oAuth2UserRequest);
        assertNotNull(result);
    }
}