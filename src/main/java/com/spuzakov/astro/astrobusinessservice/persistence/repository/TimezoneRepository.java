package com.spuzakov.astro.astrobusinessservice.persistence.repository;

import com.spuzakov.astro.astrobusinessservice.persistence.entity.TimezonePolygonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Repository
public interface TimezoneRepository extends JpaRepository<TimezonePolygonEntity, Integer> {

  @Query(value = """
          select t.tzid
          from timezone_polygons t
          where ST_Contains(t.geom, ST_SetSRID(ST_Point(:lon, :lat), 4326)) = true
      """, nativeQuery=true)
  String findTimezone(double lon, double lat);
}
