package com.jungsuk_2_1.postory.service;

import java.util.*;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import com.jungsuk_2_1.postory.dao.EmailAuthDao;
import com.jungsuk_2_1.postory.dao.UserDao;
import com.jungsuk_2_1.postory.dto.EmailAuthDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailAuthServiceImpl implements EmailAuthService {

    JavaMailSender emailSender;
    UserDao userDao;
    EmailAuthDao emailAuthDao;

    EmailAuthServiceImpl(JavaMailSender emailSender, UserDao userDao, EmailAuthDao emailAuthDao, EmailKeyProvider emailKeyProvider) {
        this.emailSender = emailSender;
        this.userDao = userDao;
        this.emailAuthDao = emailAuthDao;
    }

    private MimeMessage createMessage(String to, String certino) throws Exception {
        System.out.println("보내는 대상 : " + to);
        System.out.println("인증 번호 : " + certino);
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("이메일 인증 테스트");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> 안녕하세요 POSTORY입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다.<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += certino + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("leading.advertisement@gmail.com", "semin"));//보내는 사람

        return message;
    }


    @Override
    public void sendSimpleMessage(EmailAuthDto emailAuthDto) throws Exception {
        // TODO Auto-generated method stub
        //인증번호를 보낼 이메일을 입력
        MimeMessage message = createMessage(emailAuthDto.getEmail(), emailAuthDto.getCertino());
        try {//예외처리
            //이메일을 보내고
            emailSender.send(message);

            //인증번호 레코드를 DB에 업데이트.
            emailAuthDao.updateEmailAuth(emailAuthDto);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        return userDao.findByUserId(userId);
    }

    @Override
    public Date getExpireTime(EmailAuthDto emailAuthDto) {
        return emailAuthDao.findExpireTimeByUser(emailAuthDto);
    }

    @Override
    public Boolean compareCertiNo(Map<String, String> certinoCheckMap) {
        return emailAuthDao.existsByUserIdAndCertino(certinoCheckMap);
    }

    @Override
    public void changeUserStatus(EmailAuthDto userInfo) throws Exception {
        emailAuthDao.addUserStatus(userInfo.getUserId());

    }
    @Override
    public void changeUserEmail(EmailAuthDto userInfo) throws Exception {
        emailAuthDao.changeUserEmail(userInfo);

    }
    @Override
    public Boolean CheckExistsEid(String eid) {

        return userDao.existsByUserEmail(eid);
    }
}
