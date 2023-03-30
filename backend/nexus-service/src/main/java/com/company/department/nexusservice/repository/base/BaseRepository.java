package com.company.department.nexusservice.repository.base;

import com.company.department.nexusservice.entity.base.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T, String> {
}
