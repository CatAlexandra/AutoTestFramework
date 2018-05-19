package com.palyaeva.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Resource {

    public static String getResource(String fileName) {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        URL systemResource = systemClassLoader.getResource(fileName);
        if (systemResource == null) {
            log.error("Resource {} does not exist!", fileName);
            throw new RuntimeException("Resource " + fileName + " is not exists!");
        }

        try {
            return URLDecoder.decode(systemResource.getPath(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException exception) {
            log.error("Wrong encoding!", exception);
            throw new RuntimeException("Wrong encoding!", exception);
        }
    }
}
