package com.company.department.nexusservice.service;

import com.company.department.nexusservice.common.TestDataFactory;
import com.company.department.nexusservice.exception.asset.AssetNotFoundException;
import com.company.department.nexusservice.exception.common.ForbiddenException;
import com.company.department.nexusservice.exception.common.InternalServerException;
import com.company.department.nexusservice.model.response.AssetListResponse;
import com.company.department.nexusservice.model.response.AssetResponse;
import com.company.department.nexusservice.model.response.common.CommonApiResponse;
import com.company.department.nexusservice.model.response.common.CommonListResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssetServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private AssetService assetService;

    @Test
    void getAll_continuationTokenIsNull_shouldReturnAssets() {
        String expectedPath = "test/Test2 - Copy (4).txt";

        try {
            AssetListResponse<AssetResponse> jsonData = new ObjectMapper().readValue(TestDataFactory.createListAssetsData(), new TypeReference<>() {
            });
            ResponseEntity<AssetListResponse<AssetResponse>> entity = new ResponseEntity<>(jsonData, HttpStatus.OK);

            Mockito.when(restTemplate.exchange(
                    ArgumentMatchers.any(URI.class),
                    ArgumentMatchers.any(HttpMethod.class),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(new ParameterizedTypeReference<AssetListResponse<AssetResponse>>() {
                    })))
                    .thenReturn(entity);

            CommonListResponse<AssetResponse> commonListResponse = assetService.getAll(null);

            assertNotNull(commonListResponse.getData());
            assertEquals(1, commonListResponse.getData().size());
            assertNull(commonListResponse.getContinuationToken());
            assertEquals(expectedPath, commonListResponse.getData().get(0).getPath());
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getAll_continuationTokenIsNotNull_shouldReturnAssets() {
        String expectedPath = "test/Test2 - Copy (4).txt";
        String expectedPath2 = "test/Test2 - Copy (6).txt";

        try {
            AssetListResponse<AssetResponse> jsonData = new ObjectMapper().readValue(TestDataFactory.createListAssetsDataWithContinuationToken(), new TypeReference<>() {
            });
            ResponseEntity<AssetListResponse<AssetResponse>> entity = new ResponseEntity<>(jsonData, HttpStatus.OK);

            Mockito.when(restTemplate.exchange(
                    ArgumentMatchers.any(URI.class),
                    ArgumentMatchers.any(HttpMethod.class),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(new ParameterizedTypeReference<AssetListResponse<AssetResponse>>() {
                    })))
                    .thenReturn(entity);

            CommonListResponse<AssetResponse> commonListResponse = assetService.getAll("dfbef09efe1644ea787b1bdb5bd3df99");

            assertNotNull(commonListResponse.getData());
            assertEquals(5, commonListResponse.getData().size());
            assertNull(commonListResponse.getContinuationToken());
            assertEquals(expectedPath, commonListResponse.getData().get(0).getPath());
            assertEquals(expectedPath2, commonListResponse.getData().get(1).getPath());
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getById_idIsNotNull_shouldReturnAsset() {

        try {
            AssetResponse jsonData = new ObjectMapper().readValue(TestDataFactory.createGetAssetData(), AssetResponse.class);
            ResponseEntity<AssetResponse> entity = new ResponseEntity<>(jsonData, HttpStatus.OK);

            Mockito.when(restTemplate.getForEntity(
                    ArgumentMatchers.any(URI.class),
                    ArgumentMatchers.<Class<AssetResponse>>any())).thenReturn(entity);

            CommonApiResponse<AssetResponse> assetResponse = assetService.getById("id");

            assertNotNull(assetResponse.getData());
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void getById_httpStatusIsNotFound_shouldThrowAssetNotFoundException() {

        Mockito.when(restTemplate.getForEntity(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.<Class<AssetResponse>>any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        AssetNotFoundException exception = assertThrows(AssetNotFoundException.class, () -> assetService.getById("id"));

        assertNotNull(exception);
    }

    @Test
    void getById_httpStatusIsUnprocessableEntity_shouldThrowAssetNotFoundException() {

        Mockito.when(restTemplate.getForEntity(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.<Class<AssetResponse>>any())).thenThrow(new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY));

        AssetNotFoundException exception = assertThrows(AssetNotFoundException.class, () -> assetService.getById("id"));

        assertNotNull(exception);
    }

    @Test
    void getById_httpStatusIsInternalServerError_shouldThrowInternalServerException() {

        Mockito.when(restTemplate.getForEntity(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.<Class<AssetResponse>>any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        InternalServerException exception = assertThrows(InternalServerException.class, () -> assetService.getById("id"));

        assertNotNull(exception);
    }

    @Test
    void deleteById_shouldDeleteAssetById() {
        Mockito.doNothing().when(restTemplate).delete(ArgumentMatchers.any(URI.class));

        CommonApiResponse response = assetService.deleteById("id");

        assertTrue((Boolean) response.getData());
    }

    @Test
    void deleteById_idIsWrong_shouldThrowAssetNotFoundException() {
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND)).when(restTemplate).delete(ArgumentMatchers.any(URI.class));

        AssetNotFoundException exception = assertThrows(AssetNotFoundException.class, () -> assetService.deleteById("id"));

        assertNotNull(exception);
    }

    @Test
    void deleteById_httpStatusUnprocessableEntity_shouldThrowAssetNotFoundException() {
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY)).when(restTemplate).delete(ArgumentMatchers.any(URI.class));

        AssetNotFoundException exception = assertThrows(AssetNotFoundException.class, () -> assetService.deleteById("id"));

        assertNotNull(exception);
    }

    @Test
    void deleteById_httpStatusForbidden_shouldThrowForbiddenException() {
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN)).when(restTemplate).delete(ArgumentMatchers.any(URI.class));

        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> assetService.deleteById("id"));

        assertNotNull(exception);
    }

    @Test
    void deleteById_httpStatusIsInternalServerError_shouldThrowInternalServerException() {
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(restTemplate).delete(ArgumentMatchers.any(URI.class));

        InternalServerException exception = assertThrows(InternalServerException.class, () -> assetService.deleteById("id"));

        assertNotNull(exception);
    }

    @Test
    void search_shouldFoundAssetsAccordingToPathName() {
        try {
            AssetListResponse<AssetResponse> jsonData = new ObjectMapper().readValue(TestDataFactory.createListAssetsData(), new TypeReference<>() {
            });
            ResponseEntity<AssetListResponse<AssetResponse>> entity = new ResponseEntity<>(jsonData, HttpStatus.OK);

            Mockito.when(restTemplate.exchange(
                    ArgumentMatchers.any(URI.class),
                    ArgumentMatchers.any(HttpMethod.class),
                    ArgumentMatchers.any(),
                    ArgumentMatchers.eq(new ParameterizedTypeReference<AssetListResponse<AssetResponse>>() {
                    })
            )).thenReturn(entity);

            CommonListResponse<AssetResponse> result = assetService.search("query");

            assertFalse(result.getData().isEmpty());
        } catch (JsonProcessingException e) {
            Assertions.fail(e.getMessage());
        }

    }

    @Test
    void search_httpClientErrorException_shouldThrowInternalServerException() {
        Mockito.doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(restTemplate).exchange(
                ArgumentMatchers.any(URI.class),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(new ParameterizedTypeReference<AssetListResponse<AssetResponse>>() {
                })
        );

        InternalServerException exception = assertThrows(InternalServerException.class, () -> assetService.search("query"));

        assertNotNull(exception);
    }
}
