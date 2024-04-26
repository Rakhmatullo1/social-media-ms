package com.rakhmatullo.postsservice.service.impl;

import com.rakhmatullo.postsservice.config.StorageConfiguration;
import com.rakhmatullo.postsservice.service.FileService;
import com.rakhmatullo.postsservice.service.exceptions.CustomException;
import com.rakhmatullo.postsservice.service.exceptions.NotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService{

    private final Path photoPath;
    private final Map<String, String> contentTypes;

    public FileServiceImpl(StorageConfiguration storageConfiguration) {
        this.photoPath = Paths.get(storageConfiguration.getLocation());
        contentTypes = storageConfiguration.getSupportedMimeTypes();
    }

    @Override
    public Optional<Resource> upload(MultipartFile file) {
        log.info("Requested to upload photo");

        if(Objects.isNull(file) || file.isEmpty()) {
            log.warn("File cannot be null or null");
            throw new CustomException("File cannot be null");
        }

        Path fileLocation = generatePhotoLocation(file.getContentType());

        try {
            long size = Files.copy(file.getInputStream(), fileLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("Successfully stored new post {}", size);
            return Optional.of( new UrlResource(fileLocation.toUri()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Resource> loadFile(String pathName) {
        log.info("Requested to load file from directory");

        if(StringUtils.isBlank(pathName)) {
            log.warn("Provided path cannot be null");
            throw new CustomException("Invalid argument for path");
        }

        Path path = Paths.get(pathName);

        try {
            Resource resource = new UrlResource(path.toString());

            log.info("Successfully loaded file from directory");
            return Optional.of(resource);
        } catch (MalformedURLException e) {
            log.warn("Provided path name is not found");
            throw new NotFoundException("File is not found");
        }
    }

    private Path generatePhotoLocation(String contentType) {
        String fileName = String.valueOf(UUID.randomUUID());

        if(StringUtils.isBlank(contentType)) {
            log.warn("Content type cannot be null");
            throw new CustomException("Content type cannot be null");
        }

        if(!contentTypes.containsKey(contentType)) {
            log.warn("Unsupported content type! Given content type: {} | Supported content-types: {}", contentType, contentTypes);
            throw new CustomException("Content type is not supported");
        }
        return photoPath.resolve(fileName+contentTypes.get(contentType));
    }
}
