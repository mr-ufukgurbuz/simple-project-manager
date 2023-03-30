package com.company.department.nexusservice.common;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class TestDataFactory {

    public static String createListAssetsData() {
        return readDataFile("listAssets.json");
    }

    public static String createListAssetsDataWithContinuationToken() {
        return readDataFile("listAssetsWithContinuationToken.json");
    }

    public static String createGetAssetData() {
        return readDataFile("listAssetsWithContinuationToken.json");
    }

    public static MockMultipartFile creatMockMultipartFile() {
        String name = UUID.randomUUID().toString();
        return new MockMultipartFile(name, name, MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes());
    }

    private static String readDataFile(String fileName) {
        String content = "";
        File file;
        try {
            file = ResourceUtils.getFile("classpath:data/" + fileName);
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}
