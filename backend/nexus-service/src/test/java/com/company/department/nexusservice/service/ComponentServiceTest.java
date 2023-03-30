package com.company.department.nexusservice.service;

import com.company.department.nexusservice.common.TestDataFactory;
import com.company.department.nexusservice.exception.asset.AssetNotFoundException;
import com.company.department.nexusservice.exception.common.InternalServerException;
import com.company.department.nexusservice.model.response.AssetResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ComponentServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private ComponentService componentService;

    @Test
    void uploadComponent_shouldUpload() {
        MockMultipartFile mockMultipartFile = TestDataFactory.creatMockMultipartFile();
        ResponseEntity<String> entity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Mockito.when(restTemplate.postForEntity(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(entity);

        componentService.uploadComponent(mockMultipartFile, "dir", mockMultipartFile.getName());
    }

    @Test
    void uploadComponent_fileNameIsNull_shouldUpload() {
        MockMultipartFile mockMultipartFile = TestDataFactory.creatMockMultipartFile();
        ResponseEntity<String> entity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Mockito.when(restTemplate.postForEntity(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any())).thenReturn(entity);

        componentService.uploadComponent(mockMultipartFile, "dir", null);
    }

    @Test
    void uploadComponent_httpClientException_shouldThrowInternalServerException() {
        MockMultipartFile mockMultipartFile = TestDataFactory.creatMockMultipartFile();

        Mockito.when(restTemplate.postForEntity(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(InternalServerException.class, () -> componentService.uploadComponent(mockMultipartFile, "dir", null));
    }

}
