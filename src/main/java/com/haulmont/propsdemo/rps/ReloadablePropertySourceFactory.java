package com.haulmont.propsdemo.rps;

import com.haulmont.propsdemo.ps.ReloadablePropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReloadablePropertySourceFactory extends DefaultPropertySourceFactory {

    private static final Logger propSourceLogger = LoggerFactory.getLogger(ReloadablePropertySource.class);

    public static Map<String, ReloadablePropertySource> reloadablePropertySourceMap = new ConcurrentHashMap<>();

    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource)
            throws IOException {
        Resource internal = encodedResource.getResource();

        if (internal instanceof FileSystemResource fsr) {
            var reloadablePropertySource = new ReloadablePropertySource(s, fsr.getPath());

            reloadablePropertySourceMap.put(reloadablePropertySource.getName(), reloadablePropertySource);
            return reloadablePropertySource;
        }

        if (internal instanceof FileUrlResource fur) {
            var reloadablePropertySource = new ReloadablePropertySource(s, fur.getURL().getPath());

            reloadablePropertySourceMap.put(reloadablePropertySource.getName(), reloadablePropertySource);
            return reloadablePropertySource;
        }

        return super.createPropertySource(s, encodedResource);
    }
}