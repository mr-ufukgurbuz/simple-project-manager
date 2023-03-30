package com.company.department.nexusservice.controller;

import com.company.department.nexusservice.model.response.AssetResponse;
import com.company.department.nexusservice.model.response.common.CommonApiResponse;
import com.company.department.nexusservice.model.response.common.CommonListResponse;
import com.company.department.nexusservice.service.AssetService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("assets")
@Api(value = "Nexus Asset API")
@Validated
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<CommonListResponse> getAllAssets(@RequestParam(value = "token", required = false) @Size(min = 32) String continuationToken) {
        CommonListResponse<AssetResponse> assets = assetService.getAll(continuationToken);

        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommonApiResponse> getAsset(@PathVariable(value = "id") @Size(min = 52) String id) {
        CommonApiResponse assetResponse = assetService.getById(id);

        return new ResponseEntity<>(assetResponse, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonApiResponse> deleteAsset(@PathVariable @Size(min = 52) String id) {
        CommonApiResponse deletedAsset = assetService.deleteById(id);

        return new ResponseEntity<>(deletedAsset, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<CommonListResponse> search(@RequestParam String query) {
        CommonListResponse searchListResponse = assetService.search(query);

        return new ResponseEntity<>(searchListResponse, HttpStatus.OK);
    }
}
