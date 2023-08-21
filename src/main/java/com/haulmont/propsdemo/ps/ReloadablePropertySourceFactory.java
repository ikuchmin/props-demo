package com.haulmont.propsdemo.ps;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

public class ReloadablePropertySourceFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource)
            throws IOException {
        Resource internal = encodedResource.getResource();

        if (internal instanceof FileSystemResource fsr)
            return new ReloadablePropertySource(s, fsr.getPath());

        if (internal instanceof FileUrlResource fur) {
            return new ReloadablePropertySource(s, fur.getURL().getPath());
        }

        return super.createPropertySource(s, encodedResource);
    }
}