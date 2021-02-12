package com.github.vndovr.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO that contains new generated tokens", required = true)
public class LoginResponseDto {

  @Schema(description = "Access token for API calls", required = true)
  private String accessToken;

  @Schema(description = "Refresh token to get new pair of access and refresh tokens",
      required = true)
  private String refreshToken;
}
