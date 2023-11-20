package com.jungsuk_2_1.postory.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jungsuk_2_1.postory.dto.FileDto;
import com.jungsuk_2_1.postory.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

//import com.spring.multipart.dto.FoodDto;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private FileService fileService;

    @Autowired
    FileController(AmazonS3Client amazonS3Client, FileService fileService){
        this.amazonS3Client = amazonS3Client;
        this.fileService = fileService;
    }
    @PostMapping("/uploadFiles")
    public ResponseEntity<Object> uploadFiles(MultipartFile[] multipartFiles) {

//        String UPLOAD_PATH = "/Users/ho/Workspace/sanwangjapki/postory_front/public/images";

        List<Object> urls = new ArrayList<>();
        List<FileDto> fileList = new ArrayList<>();

        try {
            for (MultipartFile file : multipartFiles) {

                String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
                String originName = file.getOriginalFilename(); // ex) 파일.jpg
                String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
                originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
                long fileSize = file.getSize(); // 파일 사이즈

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(fileExtension);
                metadata.setContentLength(fileSize);
                System.out.println("bucket = "+ bucket);
//                String fileUrl = "https://" + bucket + "/"+fileId + "." + fileExtension;

                String fileUrl = "https://postory-image.s3.ap-northeast-2.amazonaws.com/src/"+ fileId + "."+ fileExtension;
                System.out.println("fileUrl = " + fileUrl);
                amazonS3Client.putObject(bucket,fileId+"."+fileExtension, file.getInputStream(),metadata);

//                File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
//                if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
//                    fileSave.mkdirs();
//                }
//
//                file.transferTo(fileSave); // fileSave의 형태로 파일 저장
                urls.add(fileUrl);

                System.out.println("fileId= " + fileId);
                System.out.println("originName= " + originName);
                System.out.println("fileExtension= " + fileExtension);
                System.out.println("fileSize= " + fileSize);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Fail");
        }
        Map<String,Object> data = new HashMap<>();

        data.put("urls", urls);

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/uploadThumn")
    public ResponseEntity<Object> uploadThumn(MultipartFile file) {
//        String UPLOAD_PATH = "/Users/ho/Workspace/sanwangjapki/postory_front/public/images";

        String url;

        System.out.println("file = " + file);

        try {

            String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
            String originName = file.getOriginalFilename(); // ex) 파일.jpg
            String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
            originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
            long fileSize = file.getSize(); // 파일 사이즈

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(fileSize);
            System.out.println("bucket = "+ bucket);fee_detail
//            https://postory-image.s3.ap-northeast-2.amazonaws.com/src/16895093343242601.png

            String fileUrl = "https://postory-image.s3.ap-northeast-2.amazonaws.com/src/"+ fileId + "."+ fileExtension;
            amazonS3Client.putObject(bucket,fileId+"."+ fileExtension,file.getInputStream(),metadata);

//            File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
//            if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
//                fileSave.mkdirs();
//            }

//            file.transferTo(fileSave); // fileSave의 형태로 파일 저장
//            url = fileId + "." + fileExtension;
//            url = fileUrl + "." + fileExtension;
            url = fileUrl;

//
            System.out.println("fileId= " + fileId);
            System.out.println("originName= " + originName);
            System.out.println("fileExtension= " + fileExtension);
            System.out.println("fileSize= " + fileSize);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Fail");
        }
        Map<String,Object> data = new HashMap<>();

        data.put("url", url);

        return ResponseEntity.ok().body(data);
    }
}