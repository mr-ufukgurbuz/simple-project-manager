package com.company.department.nexusservice.service;

import com.company.department.nexusservice.exception.asset.AssetNotFoundException;
import com.company.department.nexusservice.exception.common.ForbiddenException;
import com.company.department.nexusservice.exception.common.InternalServerException;
import com.company.department.nexusservice.model.response.AssetListResponse;
import com.company.department.nexusservice.model.response.AssetResponse;
import com.company.department.nexusservice.model.response.common.CommonApiResponse;
import com.company.department.nexusservice.model.response.common.CommonListResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AssetService extends NexusService {
    private static Logger logger = LoggerFactory.getLogger(AssetService.class);

    private final String SEARCH_PATH = "/search";

    public AssetService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public CommonListResponse getAll(String continuationToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(nexusUrl + assetsPath).queryParam("repository", project-packageRepository);

        if (StringUtils.isNoneBlank(continuationToken)) {
            builder.queryParam("continuationToken", continuationToken);
        }

        ResponseEntity<AssetListResponse<AssetResponse>> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        CommonListResponse<AssetResponse> commonListResponse = CommonListResponse.response(null, null);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            AssetListResponse<AssetResponse> assetList = response.getBody();

            commonListResponse = CommonListResponse.response(assetList.getItems(), assetList.getContinuationToken());
        }

        return commonListResponse;
    }

    public CommonApiResponse getById(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(nexusUrl).path(assetsPath).path("/{id}");

        try {
            ResponseEntity<AssetResponse> response = restTemplate.getForEntity(builder.build(id), AssetResponse.class);

            AssetResponse asset = null;

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                asset = response.getBody();
            }

            return CommonApiResponse.response(asset);
        } catch (HttpClientErrorException e) {
            logger.error(e.getMessage());

            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value() || e.getRawStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new AssetNotFoundException(id);
            }
            throw new InternalServerException();
        }
    }

    public CommonApiResponse deleteById(String id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(nexusUrl).path(assetsPath).path("/{id}");

        try {
            restTemplate.delete(builder.build(id));

            return CommonApiResponse.response(true);
        } catch (HttpClientErrorException e) {
            logger.error(e.getMessage());

            if (e.getRawStatusCode() == HttpStatus.NOT_FOUND.value() || e.getRawStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                throw new AssetNotFoundException(id);
            } else if (e.getRawStatusCode() == HttpStatus.FORBIDDEN.value()) {
                throw new ForbiddenException(id);
            }
            throw new InternalServerException();
        }
    }

    public CommonListResponse search(String query) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(nexusUrl + SEARCH_PATH + assetsPath)
                .queryParam("repository", project-packageRepository)
                .queryParam("q", query);

        try {
            ResponseEntity<AssetListResponse<AssetResponse>> response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            AssetListResponse<AssetResponse> body = response.getBody();
            CommonListResponse<AssetResponse> listResponse = CommonListResponse.response(body.getItems(), body.getContinuationToken());

            return listResponse;
        } catch (HttpClientErrorException e) {
            logger.error(e.getMessage());
            throw new InternalServerException();
        }
    }

}
