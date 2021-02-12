package com.github.vndovr.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "keycloak-users",
    url = "${keycloak.auth-server-url}admin/realms/${keycloak.realm}/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface KeycloakUsersClient {

  @GET
  public UserRepresentation[] getUsers(@QueryParam("search") String search);
}
