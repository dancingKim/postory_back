package com.jungsuk_2_1.postory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jungsuk_2_1.postory.config.OAuthAttributes;
import com.jungsuk_2_1.postory.dao.UserDao;
import com.jungsuk_2_1.postory.dto.UserDto;
import com.jungsuk_2_1.postory.security.ApplicationOAuth2User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuthUserService extends DefaultOAuth2UserService {
    private final UserDao userDao;
    private OAuth2User oAuth2User;

    @Autowired
    CustomOAuthUserService(UserDao userDao){
        super();
        this.userDao = userDao;
    }
    public void setOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    private UserDto saveOrUpdate(OAuthAttributes attributes) {
        UserDto user;
        if(userDao.findByUserEmail(attributes.getEmail())!=null){
            user=userDao.findByUserEmail(attributes.getEmail());
            log.info("user By Email = {}",user);
        }
        else {
            user=attributes.toEntity();
            log.info("user entity made my entity= {}",user);
            userDao.save(user);
            user=userDao.findByUserEmail(attributes.getEmail());
            log.info("user By Email = {}",user);
        }
        return user;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        if(oAuth2User == null){
            oAuth2User = super.loadUser(userRequest);
        }
        try {
            log.info("OAuth2User attributes {} ", new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        String authProvider = userRequest.getClientRegistration().getClientName();
        log.info("client name is {}, and id is {}",authProvider,registrationId);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        log.info("userNameAttributeName = {}",userNameAttributeName);
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        UserDto user = saveOrUpdate(attributes);
        // SessioUser: 세션에 사용자 정보를 저장하기 위한 DTO 클래스 (개발자가 생성)
        log.info("Successfully pulled user info username {} authProvider {}", attributes.getName(), authProvider);
        return new ApplicationOAuth2User(
                user.getUserId(),
                oAuth2User.getAttributes()
        );
    }
}