package com.jungsuk_2_1.postory.config;

import com.jungsuk_2_1.postory.dto.Role;
import com.jungsuk_2_1.postory.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey, name, email;

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public UserDto toEntity () {
        return UserDto.builder()
                .nic(name)
                .eid(email)
                .roleKey(Role.USER.getKey())
                .build();
    }
}

