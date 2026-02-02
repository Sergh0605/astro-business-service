package com.spuzakov.astro.astrobusinessservice.client.geonames.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoNamesChildrenResponse {
  private List<GeoName> geonames;

  @Data
  @Builder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class GeoName {
    private Long geonameId;
    private String name;
    private String toponymName;
    private String countryCode;
    private String fcode; // ADM1, ADM2, PPL и т.д.
    private String fcl; // A, P, S, H
    private Double lat;
    private Double lng;
  }
}
