package com.jungsuk_2_1.postory.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithMockCustomOAuth2UserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockCustomOAuth2User> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomOAuth2User customOAuth2User) {

        // 1
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // 2
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", customOAuth2User.username());
        attributes.put("name", customOAuth2User.name());
        attributes.put("email", customOAuth2User.email());
        attributes.put("picture", customOAuth2User.picture());

        // 3
        OAuth2User principal = new DefaultOAuth2User(
                List.of(new OAuth2UserAuthority(customOAuth2User.role(), attributes)),
                attributes,
                customOAuth2User.name());

        // 4
        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
                principal,
                principal.getAuthorities(),
                customOAuth2User.registrationId());

        // 5
        context.setAuthentication(token);
        return context;
    }
}