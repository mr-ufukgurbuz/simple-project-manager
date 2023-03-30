package com.company.department.nexusservice.exception.asset;

import com.company.department.nexusservice.exception.base.BusinessException;
import org.springframework.http.HttpStatus;

public class AssetNotFoundException extends BusinessException
{
    public AssetNotFoundException(String assetId)
    {
        super(HttpStatus.NOT_FOUND, String.format("Asset %s not found", assetId));
    }
}
