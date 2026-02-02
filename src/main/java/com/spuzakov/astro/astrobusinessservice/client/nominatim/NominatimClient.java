package com.spuzakov.astro.astrobusinessservice.client.nominatim;

import com.spuzakov.astro.astrobusinessservice.client.nominatim.dto.NominatimPlaceDto;
import java.util.List;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@FeignClient(
    name = "nominatimClient",
    url = "${app.nominatim.base-url}",
    configuration = NominatimFeignConfig.class
)
public interface NominatimClient {

  @GetMapping("/search")
  List<NominatimPlaceDto> search(
      @RequestParam("q") String query,
      @RequestParam("format") String format,
      @RequestParam("limit") int limit,
      @RequestParam("addressdetails") int addressDetails
  );

  default Optional<NominatimPlaceDto> searchOne(String query) {
    return search(query, "json", 1, 1).stream()
        .findFirst();
  }

}
