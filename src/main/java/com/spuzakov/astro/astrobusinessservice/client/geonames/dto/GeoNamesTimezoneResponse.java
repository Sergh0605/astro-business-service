package com.spuzakov.astro.astrobusinessservice.client.geonames.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoNamesTimezoneResponse {
  private String timezoneId;
  private String time;
  private Double gmtOffset;
  private Double dstOffset;
}
