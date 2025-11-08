package com.aura.service.repository;

import com.aura.service.entity.ManagedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagedEntityRepository extends JpaRepository<ManagedEntity, Long> {
}
