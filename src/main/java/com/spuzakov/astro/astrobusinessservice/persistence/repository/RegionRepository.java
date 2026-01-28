package com.spuzakov.astro.astrobusinessservice.persistence.repository;

import com.spuzakov.astro.astrobusinessservice.persistence.entity.CountryEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.RegionEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, UUID> {
  List<RegionEntity> findAllByCountryId(UUID countryId);
  List<RegionEntity> findAllByCountryIdAndInitializedIsFalse(UUID countryId);
}
