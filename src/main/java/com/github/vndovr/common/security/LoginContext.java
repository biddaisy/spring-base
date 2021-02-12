package com.github.vndovr.common.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Wraps and extracts information about current user
 * 
 * @author Vladimir Radchuk
 *
 */
@Component
public class LoginContext {

  /**
   * Returns the user id of logged in user
   * 
   * @return
   */
  public String getUserId() {
    return Optional.ofNullable(keycloakSecurityContext())
        .map(keycloakSecurityContext -> keycloakSecurityContext.getToken().getPreferredUsername())
        .orElse("anonymouse");
  }

  /**
   * Returns the list of roles assigned to user
   * 
   * @return
   */
  public Set<String> getRoles() {
    return Optional.ofNullable(keycloakSecurityContext()).map(
        keycloakSecurityContext -> keycloakSecurityContext.getToken().getRealmAccess().getRoles())
        .orElse(Collections.emptySet());
  }

  /**
   * Returns the list of groups assigned to user
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public Set<String> getGroups() {
    return Optional.ofNullable(keycloakSecurityContext())
        .map(keycloakSecurityContext -> (Set<String>) new HashSet<String>(
            (ArrayList<String>) keycloakSecurityContext.getToken().getOtherClaims()
                .getOrDefault("groups", Collections.emptySet())))
        .orElse(Collections.emptySet());
  }


  /**
   * Checks if user has specific role assigned
   * 
   * @param role
   * @return
   */
  public boolean hasRole(String... roles) {
    Set<String> userRoles = getRoles();
    for (String role : roles) {
      if (userRoles.contains(role)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the user name
   * 
   * @return
   */
  public String getName() {
    return Optional.ofNullable(keycloakSecurityContext())
        .map(keycloakSecurityContext -> keycloakSecurityContext.getToken().getName()).orElse("");
  }

  /**
   * Returns the user email
   * 
   * @return
   */
  public String getUserEmail() {
    return Optional.ofNullable(keycloakSecurityContext())
        .map(keycloakSecurityContext -> keycloakSecurityContext.getToken().getEmail()).orElse("");
  }

  /**
   * Returns the username
   * 
   * @return
   */
  public String getFirstName() {
    return Optional.ofNullable(keycloakSecurityContext())
        .map(keycloakSecurityContext -> keycloakSecurityContext.getToken().getGivenName())
        .orElse("");
  }

  /**
   * Returns the username
   * 
   * @return
   */
  public String getLastName() {
    return Optional.ofNullable(keycloakSecurityContext())
        .map(keycloakSecurityContext -> keycloakSecurityContext.getToken().getFamilyName())
        .orElse("");
  }

  /**
   * Returns the keycloack security context if one exists
   * 
   * @return
   */
  public KeycloakSecurityContext keycloakSecurityContext() {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    return request.getUserPrincipal() instanceof KeycloakAuthenticationToken
        ? (KeycloakSecurityContext) ((KeycloakAuthenticationToken) request.getUserPrincipal())
            .getCredentials()
        : null;
  }
}
