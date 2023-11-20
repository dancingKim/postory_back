package com.jungsuk_2_1.postory.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void testPrivateMethod() throws Exception {
        JwtAuthenticationFilter obj = new JwtAuthenticationFilter(tokenProvider);
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("parseBearerToken", HttpServletRequest.class);
        ((Method) method).setAccessible(true);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer token");

        String result = (String) method.invoke(obj, mockRequest);

        assertEquals("token", result);
    }

    @Test
    public void testDoFilterInternal() throws ServletException, IOException {
        // Arrange
        TokenProvider mockTokenProvider = mock(TokenProvider.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        FilterChain mockFilterChain = mock(FilterChain.class);

        TestableJwtAuthenticationFilter filter = new TestableJwtAuthenticationFilter(mockTokenProvider);

        // Act
        filter.testableDoFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // Assert
        // Here you would use assertions to verify the behavior of doFilterInternal
    }
}
