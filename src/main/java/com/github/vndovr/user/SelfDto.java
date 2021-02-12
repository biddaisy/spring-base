package com.github.vndovr.user;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO contains information about current user settings")
public class SelfDto {

  @Schema(description = "user's login")
  private String username;

  @Schema(description = "user's first name")
  private String firstName;

  @Schema(description = "user's last name")
  private String lastName;

  @Schema(description = "user's email address")
  private String email;

  @Schema(description = "list of roles this user hase")
  private Set<String> roles;

  @Schema(description = "List of groups this user belongs to")
  private Set<String> groups;

}
