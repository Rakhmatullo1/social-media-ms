package com.rakhmatullo.postsservice.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StorageProperties {

    @Value("${storage.location}")
    private String postLocation;

    public String getPostLocation() {
        return postLocation;
    }
}
