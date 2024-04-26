package com.rakhmatullo.postsservice.config;

import com.rakhmatullo.postsservice.properties.StorageProperties;
import com.rakhmatullo.postsservice.service.enums.MimeType;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j

public class StorageConfiguration {

    private final StorageProperties storageProperties;
    private final Map<String, String> contentTypes;

    public StorageConfiguration(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        contentTypes = getContentTypes();
    }

    @PostConstruct
    void initializeDirectory() {
        log.info("Start creating storage directory...");
        File storageDirectory = new File(storageProperties.getPostLocation());

        if(!storageDirectory.exists()) {
            boolean isDirectoryCreated = storageDirectory.mkdirs();

            log.info("Is directory created? Result: {}", isDirectoryCreated);
        }else {
            log.info("Storage directory is already created. Path: {}", storageDirectory.getAbsolutePath());
        }
    }

    public String getLocation() {
        return storageProperties.getPostLocation();
    }

    public Map<String, String> getSupportedMimeTypes() {
        return contentTypes;
    }

    private Map<String, String> getContentTypes() {
        Map<String, String> contentTypes = new HashMap<>();
        Arrays.stream(MimeType.values()).forEach(type->contentTypes.put(type.getType().toString(), type.getExtension()));
        return contentTypes;
    }
}
