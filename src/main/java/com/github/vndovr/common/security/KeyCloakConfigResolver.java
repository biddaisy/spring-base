package com.github.vndovr.common.security;

import java.util.Collections;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.github.vndovr.Profiles;

@Configuration
@Profile(Profiles.STAGING)
public class KeyCloakConfigResolver extends KeycloakSpringBootConfigResolver {
  private final KeycloakDeployment keycloakDeployment;

  public KeyCloakConfigResolver(KeycloakSpringBootProperties properties,
      KeyCloakSecuritySettings config) {
    properties.setRealm(config.getKeycloakRealm());
    properties.setResource(config.getKeycloakClient());
    properties.setAuthServerUrl(config.getKeycloakUrl());
    properties.setCredentials(Collections.singletonMap("secret", config.getKeycloakSecret()));
    keycloakDeployment = KeycloakDeploymentBuilder.build(properties);
  }

  @Override
  public KeycloakDeployment resolve(HttpFacade.Request facade) {
    return keycloakDeployment;
  }
}
