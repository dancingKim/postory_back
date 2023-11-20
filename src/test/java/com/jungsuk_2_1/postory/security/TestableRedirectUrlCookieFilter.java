package com.jungsuk_2_1.postory.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class TestableRedirectUrlCookieFilter extends RedirectUrlCookieFilter {

    public TestableRedirectUrlCookieFilter(){
        super();
    }

    public void testableDoFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.doFilterInternal(request, response, filterChain);
    }


}
