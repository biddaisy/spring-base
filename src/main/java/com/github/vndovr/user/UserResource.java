package com.github.vndovr.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.stereotype.Controller;
import com.github.vndovr.common.jaxrs.Descriptions;
import com.github.vndovr.common.security.LoginContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@Path("/users")
@Tag(name = "Users API",
    description = "API for reading information about user's setting and search users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  private LoginContext loginContext;

  @Path("/self")
  @GET
  @Operation(summary = "Returns the information about current user",
      security = @SecurityRequirement(name = "bearer"),
      responses = {
          @ApiResponse(responseCode = "200", description = Descriptions.D200,
              content = @Content(mediaType = javax.ws.rs.core.MediaType.APPLICATION_JSON,
                  schema = @Schema(implementation = SelfDto.class))),
          @ApiResponse(responseCode = "400", description = Descriptions.D400),
          @ApiResponse(responseCode = "401", description = Descriptions.D401),
          @ApiResponse(responseCode = "403", description = Descriptions.D403),
          @ApiResponse(responseCode = "500", description = Descriptions.D500)})
  public Response self() {
    SelfDto dto = new SelfDto();
    dto.setUsername(loginContext.getUserId());
    dto.setFirstName(loginContext.getFirstName());
    dto.setLastName(loginContext.getLastName());
    dto.setEmail(loginContext.getUserEmail());
    dto.setRoles(loginContext.getRoles());
    dto.setGroups(loginContext.getGroups());
    return Response.ok(dto).build();
  }
}
