package com.github.vndovr.authentication;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.keycloak.OAuth2Constants;
import org.keycloak.constants.ServiceUrlConstants;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;
import com.github.vndovr.common.security.KeyCloakSecuritySettings;
import lombok.AllArgsConstructor;

/**
 * Authentication service methods
 * 
 * @author Vladimir Radchuk
 *
 */
@Service
@AllArgsConstructor
class AuthenticationService {

  private KeyCloakSecuritySettings keyCloakSecuritySettings;

  private Client client;

  /**
   * Login user to the system
   * 
   * @param dto
   * @return
   */
  public LoginResponseDto login(LoginRequestDto dto) {
    Form form = new Form();
    form.param(OAuth2Constants.GRANT_TYPE, OAuth2Constants.PASSWORD);
    form.param(OAuth2Constants.CLIENT_ID, keyCloakSecuritySettings.getKeycloakClient());
    form.param(OAuth2Constants.CLIENT_SECRET, keyCloakSecuritySettings.getKeycloakSecret());
    form.param(OAuth2Constants.USERNAME, dto.getLogin());
    form.param(OAuth2Constants.PASSWORD, dto.getPassword());
    WebTarget target = client.target(keyCloakSecuritySettings.getKeycloakUrl())
        .path(ServiceUrlConstants.TOKEN_PATH)
        .resolveTemplate("realm-name", keyCloakSecuritySettings.getKeycloakRealm());
    Invocation.Builder builder = target.request();
    AccessTokenResponse response =
        builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
            .post(Entity.form(form), AccessTokenResponse.class);
    return new LoginResponseDto(response.getToken(), response.getRefreshToken());
  }

  /**
   * Refresh the JWT token
   * 
   * @param token
   * @return
   */
  public LoginResponseDto refresh(String token) {
    Form form = new Form();
    form.param(OAuth2Constants.GRANT_TYPE, OAuth2Constants.REFRESH_TOKEN);
    form.param(OAuth2Constants.CLIENT_ID, keyCloakSecuritySettings.getKeycloakClient());
    form.param(OAuth2Constants.CLIENT_SECRET, keyCloakSecuritySettings.getKeycloakSecret());
    form.param(OAuth2Constants.REFRESH_TOKEN, token);
    WebTarget target = client.target(keyCloakSecuritySettings.getKeycloakUrl())
        .path(ServiceUrlConstants.TOKEN_PATH)
        .resolveTemplate("realm-name", keyCloakSecuritySettings.getKeycloakRealm());
    Invocation.Builder builder = target.request();
    AccessTokenResponse response =
        builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
            .post(Entity.form(form), AccessTokenResponse.class);
    return new LoginResponseDto(response.getToken(), response.getRefreshToken());
  }

  /**
   * Logout user from the system (for given refresh token)
   * 
   * @param token
   */
  public void logout(String token) {
    Form form = new Form();
    form.param(OAuth2Constants.GRANT_TYPE, OAuth2Constants.REFRESH_TOKEN);
    form.param(OAuth2Constants.CLIENT_ID, keyCloakSecuritySettings.getKeycloakClient());
    form.param(OAuth2Constants.CLIENT_SECRET, keyCloakSecuritySettings.getKeycloakSecret());
    form.param(OAuth2Constants.REFRESH_TOKEN, token);
    WebTarget target = client.target(keyCloakSecuritySettings.getKeycloakUrl())
        .path(ServiceUrlConstants.TOKEN_SERVICE_LOGOUT_PATH)
        .resolveTemplate("realm-name", keyCloakSecuritySettings.getKeycloakRealm());
    Invocation.Builder builder = target.request();
    builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
        .post(Entity.form(form));
  }
}
