package com.aura.service.repository;

import com.aura.service.entity.ManagedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagedEntityRepository extends JpaRepository<ManagedEntity, Long> {
    List<ManagedEntity> findByType(String type);
}
