package com.spuzakov.astro.astrobusinessservice.persistence.repository;

import com.spuzakov.astro.astrobusinessservice.persistence.entity.GeoCacheEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Repository
public interface GeoCacheRepository extends JpaRepository<GeoCacheEntity, Long> {

  Optional<GeoCacheEntity> findByNormalizedQuery(String normalizedQuery);
}
