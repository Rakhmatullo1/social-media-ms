package com.rakhmatullo.postsservice.controller;

import com.rakhmatullo.postsservice.controller.utils.ResponseUtils;
import com.rakhmatullo.postsservice.service.ContentService;
import com.rakhmatullo.postsservice.service.dto.ContentDTO;
import com.rakhmatullo.postsservice.service.dto.MimeTypeWithResourceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/content/{id}")
    public ResponseEntity<ContentDTO> create(@PathVariable UUID id, @RequestParam("file")MultipartFile file) {
        log.debug("REST request to upload content for post {}", id);
        Optional<ContentDTO> contentDTO = contentService.storeContent(id, file);

        ResponseEntity<ContentDTO> response = ResponseUtils.wrapResponse(contentDTO);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<Resource> loadContent(@PathVariable UUID id ) {
        log.debug("REST request to load content {}", id);
        Optional<MimeTypeWithResourceDTO> resource = contentService.loadById(id);

        ResponseEntity<Resource> response = ResponseEntity.notFound().build();

        if(resource.isPresent()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(resource.get().getMimeType().getType());

            response = ResponseEntity.ok().headers(httpHeaders).body(resource.get().getResource());
        }

        log.debug("Response: {}", response);
        return response;
    }
}
