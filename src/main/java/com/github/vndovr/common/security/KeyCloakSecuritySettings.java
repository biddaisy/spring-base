package com.github.vndovr.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableAsync
@EnableScheduling
public class KeyCloakSecuritySettings {
  @Getter
  @Setter
  @Value("${keycloak.auth-server-url:}")
  private String keycloakUrl;

  @Getter
  @Setter
  @Value("${keycloak.realm:}")
  private String keycloakRealm;

  @Getter
  @Setter
  @Value("${keycloak.resource:}")
  private String keycloakClient;

  @Getter
  @Setter
  @Value("${keycloak.credentials.secret:}")
  private String keycloakSecret;
}
