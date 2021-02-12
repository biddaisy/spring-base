package com.github.vndovr.common.jaxrs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO with reference information about dependent object")
public class RefDto {

  @Schema(description = "identifier for referenced object", required = true)
  private long id;

  /**
   * Builds the reference object from id
   * 
   * @param id
   * @return
   */
  public static RefDto valueOf(long id) {
    RefDto refDto = new RefDto();
    refDto.setId(id);
    return refDto;
  }
}
