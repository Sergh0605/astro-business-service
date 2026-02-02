package com.spuzakov.astro.astrobusinessservice.client.nominatim.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimPlaceDto {
  @JsonProperty("place_id")
  private String placeId;

  @JsonProperty("display_name")
  private String displayName;

  @JsonProperty("lat")
  private Double lat;

  @JsonProperty("lon")
  private Double lon;
}
