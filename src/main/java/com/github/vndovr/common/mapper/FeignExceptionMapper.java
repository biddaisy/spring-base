package com.github.vndovr.common.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Register classes/services/mappers specific for locations
 *
 * @author Dmitry Ruchko
 *
 */
@Provider
@Slf4j
public class FeignExceptionMapper implements ExceptionMapper<FeignException> {

  @Override
  public Response toResponse(FeignException exception) {
    log.error("ConstraintViolation exception: {}", exception.getMessage());
    return Response.status(exception.status()).entity(exception.contentUTF8()).build();
  }
}
