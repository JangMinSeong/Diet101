package com.d101.back.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //saveFile 사용 시, saveFile("프론트에서 받은 multipartFile", "저장할 경로 (ex. user/username)")
    public String saveFile(MultipartFile multipartFile, String folderPath) {
        String originalFilename = multipartFile.getOriginalFilename();

        String fileKey = folderPath + "/" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        try {
            amazonS3.putObject(bucket, fileKey, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            log.error("파일 저장 중에 에러 발생 : {}" ,e.getMessage());
        }
        return amazonS3.getUrl(bucket, fileKey).toString();
    }
}
