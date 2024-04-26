package com.rakhmatullo.postsservice.service.dto;

import com.rakhmatullo.postsservice.service.enums.MimeType;
import lombok.Data;
import org.springframework.core.io.Resource;

@Data
public class MimeTypeWithResourceDTO {
    private Resource resource;
    private MimeType mimeType;
}
