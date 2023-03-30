package com.company.department.nexusservice.service;

import com.company.department.nexusservice.model.request.DeleteFolderRequest;
import com.company.department.nexusservice.model.response.AssetResponse;
import com.company.department.nexusservice.model.response.common.CommonApiResponse;
import com.company.department.nexusservice.model.response.common.CommonListResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;

import static com.company.department.nexusservice.common.TestConstant.IT_FOLDER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssetServiceIT {

    @Value("${nexus.url}")
    private String nexusUrl;

    @Value("${nexus.componentsPath}")
    private String componentsPath;

    @Value("${nexus.repository}")
    private String project-packageRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private RestTemplate restTemplate;

    private void uploadAssetFromResourcesData(final String directory, final String fileName, final int numberOfUpload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.put("raw.directory", Collections.singletonList(directory));

        try {
            for (int i = 1; i <= numberOfUpload; i++) {
                request.put(String.format("raw.asset%d", i), Collections.singletonList(new FileSystemResource(ResourceUtils.getFile("classpath:data/" + fileName))));
                request.put(String.format("raw.asset%d.filename", i), Collections.singletonList(i + fileName));
            }

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(UriComponentsBuilder.fromUriString(nexusUrl + componentsPath)
                    .queryParam("repository", project-packageRepository).build().toUri(), requestEntity, String.class);

            if (!response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                throw new RuntimeException("Could not upload the file");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found " + fileName);
        }
    }

    private void deleteFolder(String folderName) {
        DeleteFolderRequest request = new DeleteFolderRequest(Arrays.asList(folderName, project-packageRepository));
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://vdi-project-package03:8081/service/extdirect", request, String.class);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException("Could not delete folder " + folderName);
        }
    }

    @BeforeEach
    void beforeEach() {
        uploadAssetFromResourcesData(IT_FOLDER, "getAsset.json", 15);
    }

    @Test
    void getAll_continuationTokenIsNull_shouldReturnAssetsAndToken() {
        CommonListResponse response = assetService.getAll(null);

        assertEquals(response.getData().size(), 10);
        assertNotNull(response.getContinuationToken());
    }

    @Test
    void getAll_continuationTokenIsNotNull_shouldReturnAssets() {
        CommonListResponse response = assetService.getAll(null);
        String token = response.getContinuationToken();

        CommonListResponse responseWithToken = assetService.getAll(token);

        assertNotNull(responseWithToken.getData());
    }

    @Test
    void getById_idIsValid_shouldReturnAsset() {
        CommonListResponse<AssetResponse> all = assetService.getAll(null);
        String firstItemId = all.getData().get(0).getId();
        CommonApiResponse<AssetResponse> byId = assetService.getById(firstItemId);

        assertNotNull(byId.getData());
        assertNotNull(byId.getData().getId());
    }

    @Test
    void deleteById_idIsValid_shouldDeleteAsset() {
        CommonListResponse<AssetResponse> all = assetService.getAll(null);

        AssetResponse selected = null;
        for (AssetResponse asset :
                all.getData()) {
            if (asset.getPath().contains(IT_FOLDER)) {
                selected = asset;
            }
        }

        CommonApiResponse<AssetResponse> byId = assetService.getById(selected.getId());

        assertNotNull(byId.getData());
        assertNotNull(byId.getData().getId());
    }

    @Test
    void search_shouldFoundAsset() throws InterruptedException {
        String expectedFileName = "1getAsset.json";

        // Wait for uploading time
        Thread.sleep(2000);

        CommonListResponse<AssetResponse> search = assetService.search(expectedFileName);

        boolean found = false;
        for (AssetResponse response : search.getData()) {
            if (response.getPath().contains(expectedFileName)) {
                found = true;
            }
        }

        assertTrue(found);
    }

    @AfterEach
    void afterEach() {
        deleteFolder(IT_FOLDER);
    }

}
