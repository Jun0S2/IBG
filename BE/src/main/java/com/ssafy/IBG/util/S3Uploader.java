package com.ssafy.IBG.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile multipartFile, String savedName) throws Exception{

        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
        String timeStr = new SimpleDateFormat("HHmmss").format(date);

        String filePath = "image" + "/" + dateStr + "/" + timeStr;
        System.out.println(filePath);
        System.out.println(savedName);

        putS3(multipartfileToFile(multipartFile), filePath + "/" + savedName); // s3로 업로드
        removeFile(multipartFile);

        return "https://d1fbcdpbniczyu.cloudfront.net/"+filePath;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private File multipartfileToFile(MultipartFile mfile) throws IllegalStateException, IOException{
        File file = new File(mfile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mfile.getBytes());
        fos.close();
        return file;
    }

    // 로컬에 저장된 파일 지우기
    private void removeFile(MultipartFile mfile) {
        File file = new File(mfile.getOriginalFilename());
        if (file.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

}
