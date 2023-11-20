package com.jungsuk_2_1.postory.security;

import com.jungsuk_2_1.postory.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    public void testCreate() {
        UserDto userDto = new UserDto();
        userDto.setUserId("someUuid");

        String token = tokenProvider.create(userDto);

        assertNotNull(token);
    }

    @Test
    public void testValidateAndGetUserId() {
        String token = "someValidToken";
        when(tokenProvider.validateAndGetUserId(token)).thenReturn("someUserId");

        String userId = tokenProvider.validateAndGetUserId(token);

        assertEquals("someUserId", userId);
    }
}
