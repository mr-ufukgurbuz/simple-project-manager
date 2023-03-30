package com.company.department.nexusservice.service;

import com.company.department.nexusservice.exception.common.InternalServerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;

@Service
public class ComponentService extends NexusService {

    private static Logger logger = LoggerFactory.getLogger(ComponentService.class);

    public ComponentService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public void uploadComponent(MultipartFile file, String directory, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        LinkedMultiValueMap<String, Object> uploadRequest = createUploadRequest(directory, file, fileName);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(uploadRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(UriComponentsBuilder.fromUriString(nexusUrl + componentsPath)
                    .queryParam("repository", project-packageRepository).build().toUri(), requestEntity, String.class);

        } catch (HttpClientErrorException e) {
            logger.error(e.getMessage());
            throw new InternalServerException();
        }
    }

    private LinkedMultiValueMap<String, Object> createUploadRequest(final String directory, final MultipartFile file, String fileName) {
        LinkedMultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.put("raw.directory", Collections.singletonList(directory));

        try {
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), null);

            request.put("raw.asset1", Collections.singletonList(fileEntity));

            if (StringUtils.isBlank(fileName)) {
                fileName = file.getOriginalFilename();
            }

            request.put("raw.asset1.filename", Collections.singletonList(fileName));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new InternalServerException();
        }
        return request;
    }
}
