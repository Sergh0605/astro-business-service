package com.spuzakov.astro.astrobusinessservice.persistence.repository;

import com.spuzakov.astro.astrobusinessservice.persistence.entity.CityEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.CountryEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
  List<CountryEntity> findAllByInitializedIsFalse();
}
