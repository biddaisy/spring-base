package com.github.vndovr.authentication;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Controller;
import com.github.vndovr.common.jaxrs.Descriptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Controller
@Slf4j
@Path("/")
@Tag(name = "Authentication API",
    description = "API for loging and logout functionality and refreshing of JWT tokens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

  private AuthenticationService authenticationService;

  @Path("/login")
  @POST
  @Operation(summary = "Performs user login to the system",
      responses = {
          @ApiResponse(responseCode = "200", description = Descriptions.D200,
              content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
                  schema = @Schema(implementation = LoginResponseDto.class))),
          @ApiResponse(responseCode = "400", description = Descriptions.D400),
          @ApiResponse(responseCode = "401", description = Descriptions.D401),
          @ApiResponse(responseCode = "403", description = Descriptions.D403),
          @ApiResponse(responseCode = "500", description = Descriptions.D500)})
  public Response login(@Valid LoginRequestDto dto) {
    log.info("Logging in user [{}]", dto.getLogin());
    return Response.ok(authenticationService.login(dto)).build();
  }

  @Path("/logout")
  @POST
  @Operation(summary = "Performs user logout",
      responses = {@ApiResponse(responseCode = "204", description = Descriptions.D200),
          @ApiResponse(responseCode = "400", description = Descriptions.D400),
          @ApiResponse(responseCode = "401", description = Descriptions.D401),
          @ApiResponse(responseCode = "403", description = Descriptions.D403),
          @ApiResponse(responseCode = "500", description = Descriptions.D500)})
  public Response logout(@Valid RefreshTokenDto dto) {
    authenticationService.logout(dto.getToken());
    return Response.noContent().build();
  }

  @Path("/refresh")
  @POST
  @Operation(summary = "Retrieves new pair of refresh and access tokens using the refresh token",
      responses = {
          @ApiResponse(responseCode = "200", description = Descriptions.D200,
              content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
                  schema = @Schema(implementation = LoginResponseDto.class))),
          @ApiResponse(responseCode = "400", description = Descriptions.D400),
          @ApiResponse(responseCode = "401", description = Descriptions.D401),
          @ApiResponse(responseCode = "403", description = Descriptions.D403),
          @ApiResponse(responseCode = "500", description = Descriptions.D500)})
  public Response refresh(@Valid RefreshTokenDto dto) {
    return Response.ok(authenticationService.refresh(dto.getToken())).build();
  }


}
