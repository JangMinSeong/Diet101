package com.d101.back.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //saveFile 사용 시, saveFile("프론트에서 받은 multipartFile", "저장할 경로 (ex. user/username)")
    public String saveFile(MultipartFile multipartFile, String folderPath) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        String fileKey = folderPath + "/" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, fileKey, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, fileKey).toString();
    }
}
