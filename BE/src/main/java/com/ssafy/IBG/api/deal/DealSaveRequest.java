package com.ssafy.IBG.api.deal;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DealSaveRequest {
    private Integer gameNo;
    private Integer userNo;
    private String dealTitle;
    private String dealContent;
    private MultipartFile file;
    private Integer dealPrice;
}