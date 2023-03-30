package com.company.department.nexusservice.repository;

import com.company.department.nexusservice.entity.Project;
import com.company.department.nexusservice.repository.base.BaseRepository;

import java.util.Optional;


public interface ProjectRepository extends BaseRepository<Project> {
    Optional<Project> findByAssetName(String assetName);
}
