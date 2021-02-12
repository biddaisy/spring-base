package com.github.vndovr.common.mapper;

import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Register classes/services/mappers specific for locations
 *
 * @author Dmitry Ruchko
 *
 */
@Provider
@Slf4j
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

  @Override
  public Response toResponse(ValidationException exception) {
    log.error("ConstraintViolation exception: {}", exception.getMessage());
    return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
  }
}
