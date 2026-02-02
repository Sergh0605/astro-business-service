package com.spuzakov.astro.astrobusinessservice.client.nominatim;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Configuration
public class NominatimFeignConfig {
  private static final String USER_AGENT_HEADER = "User-Agent";

  @Value("${app.nominatim.user-agent-header-value}")
  private String userAgentHeaderValue;

  @Bean
  public RequestInterceptor nominatimUserAgent() {
    return template -> template.header(
        USER_AGENT_HEADER,
        userAgentHeaderValue
      );
  }

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.BASIC;
  }
}
