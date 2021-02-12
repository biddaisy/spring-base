package com.github.vndovr.common.jaxrs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.keycloak.OAuth2Constants;
import org.keycloak.TokenVerifier;
import org.keycloak.common.util.Time;
import org.keycloak.constants.ServiceUrlConstants;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.github.vndovr.Profiles;
import com.github.vndovr.common.security.KeyCloakSecuritySettings;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

/**
 * Propagates the request header if it exists or get client credentials from keycloak
 * 
 * @author Vladimir Radchuk
 *
 */
@Component
@AllArgsConstructor
@Profile(Profiles.STAGING)
public class FeignRequestInterceptor implements RequestInterceptor {

  private KeyCloakSecuritySettings keyCloakSecuritySettings;

  private Client client;

  private static final ThreadLocal<AccessData> holder = new ThreadLocal<>();

  @Override
  @SneakyThrows
  public void apply(RequestTemplate template) {
    // if we don't have Authentication tokenString in headers
    if (!template.headers().containsKey("Authorization")) {
      if (holder.get() == null || holder.get().getAccessToken().getExp() - 5 < Time.currentTime()) {
        String tokenString = generateClientToken();
        holder.set(new AccessData(tokenString,
            TokenVerifier.create(tokenString, AccessToken.class).getToken()));
      }
      template.header("Authorization", "Bearer " + holder.get().getTokenString());
    }
  }

  /**
   * Generates the client tokenString to access keycloak
   * 
   * @return
   */
  private String generateClientToken() {
    Form form = new Form();
    form.param(OAuth2Constants.GRANT_TYPE, OAuth2Constants.CLIENT_CREDENTIALS);
    form.param(OAuth2Constants.CLIENT_ID, keyCloakSecuritySettings.getKeycloakClient());
    form.param(OAuth2Constants.CLIENT_SECRET, keyCloakSecuritySettings.getKeycloakSecret());

    WebTarget target = client.target(keyCloakSecuritySettings.getKeycloakUrl())
        .path(ServiceUrlConstants.TOKEN_PATH)
        .resolveTemplate("realm-name", keyCloakSecuritySettings.getKeycloakRealm());
    Invocation.Builder builder = target.request();
    AccessTokenResponse response =
        builder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
            .post(Entity.form(form), AccessTokenResponse.class);
    return response.getToken();
  }

  @Value
  private static class AccessData {
    private String tokenString;
    private AccessToken accessToken;
  }
}
