package com.github.vndovr.authentication;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO that represents token refresh request", required = true)
public class RefreshTokenDto {

  @Schema(description = "Refresh token used to get new pair of access and refresh tokens",
      required = true)
  @NotBlank
  private String token;
}
