package com.company.department.nexusservice.service;

import com.company.department.nexusservice.common.TestDataFactory;
import com.company.department.nexusservice.model.response.AssetResponse;
import com.company.department.nexusservice.model.response.common.CommonListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static com.company.department.nexusservice.common.TestConstant.IT_FOLDER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ComponentServiceIT {

    @Autowired
    private ComponentService componentService;

    @Autowired
    private AssetService assetService;

    @Test
    void uploadComponent() throws InterruptedException {
        MockMultipartFile mockMultipartFile = TestDataFactory.creatMockMultipartFile();

        componentService.uploadComponent(mockMultipartFile, IT_FOLDER, mockMultipartFile.getOriginalFilename());

        // Wait for uploading time
        Thread.sleep(2000);

        CommonListResponse<AssetResponse> searchResult = assetService.search(mockMultipartFile.getName());

        AssetResponse foundAssetResponse = null;
        for (AssetResponse response : searchResult.getData()) {
            if (response.getPath().contains(mockMultipartFile.getName())) {
                foundAssetResponse = response;
                break;
            }
        }

        assertNotNull(foundAssetResponse);
        assetService.deleteById(foundAssetResponse.getId());
    }
}
