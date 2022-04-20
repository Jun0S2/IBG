package com.ssafy.IBG.api.deal;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class DealUtil {

    /**
     * @author : 곽현준
     * @date : 2022-03-23 오후 5:47
     * @desc : filePath에 맞는 디렉토리 생성
    **/
    public void makeDir(String filePath) {
        if (!new File(filePath).exists()) {
            try {
                new File(filePath).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * @author : 곽현준
     * @date : 2022-03-23 오후 5:47
     * @desc : 프론트 단에서 파일을 받아 확장자에 따라 파일 타입을 결정
    **/
    public String getFileType(MultipartFile files){
        String fileName = files.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        extension = extension.toLowerCase();
        if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))
            return "IMAGE";

        return null;
    }
}
