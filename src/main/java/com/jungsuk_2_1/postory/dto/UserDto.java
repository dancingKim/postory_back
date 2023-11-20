package com.jungsuk_2_1.postory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String token; //토큰 정보
    private String userStatus;
    private String userId; // 유저에게 고유하게 부여되는 uuid
    private String eid; // 아이디로 사용할 유저네임. 이메일일 수도 그냥 문자열일 수도 있다.
    private String pwd; // 패스워드.
    private String nic;
    private String userIntro;
    private String userImgPath;
    private Date signupDtm;
    private int holdPnt;
    private boolean msgAlowYn;
    private String roleKey;
    private String authProvider; // 이후 OAuth에서 사용할 유저 정보 제공자 : github
    private String currentPwd;
    private String newPwd;

    public UserDto update(String name) {
        this.nic = name;
        return this;
    }
}