package com.jungsuk_2_1.postory.security;

import com.jungsuk_2_1.postory.config.OAuthAttributes;
import com.jungsuk_2_1.postory.dao.UserDao;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class MockCustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserDao userDao;

    public MockCustomOAuth2UserService(UserDao userDao) {
        super();
        this.userDao = userDao;
    }

    private UserDto saveOrUpdate(OAuthAttributes attributes) {
        UserDto user;
        if(userDao.findByUserEmail(attributes.getEmail())!=null){
            user=userDao.findByUserEmail(attributes.getEmail());
        }
        else {
            user=attributes.toEntity();
            userDao.save(user);
            user=userDao.findByUserEmail(attributes.getEmail());
        }
        return user;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        String authProvider = userRequest.getClientRegistration().getClientName();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        UserDto user = saveOrUpdate(attributes);

        return new ApplicationOAuth2User(
                user.getUserId(),
                oAuth2User.getAttributes()
        );
    }
}
