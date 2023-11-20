package com.jungsuk_2_1.postory.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedirectUrlCookieFilterTest {

    @InjectMocks
    private RedirectUrlCookieFilter redirectUrlCookieFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Test
    public void testDoFilterInternal() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("/auth/authorize"));

        TestableRedirectUrlCookieFilter testFilter = new TestableRedirectUrlCookieFilter();

        testFilter.testableDoFilterInternal(request, response, filterChain);

        verify(response, times(1)).addCookie(any());
    }
}