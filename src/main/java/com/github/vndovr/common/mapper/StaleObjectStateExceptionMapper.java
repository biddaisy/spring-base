package com.github.vndovr.common.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.hibernate.StaleObjectStateException;
import lombok.extern.slf4j.Slf4j;

/**
 * Indicates conflict during persistence update
 * 
 * @author Vladimir Radchuk
 *
 */
@Provider
@Slf4j
public class StaleObjectStateExceptionMapper implements ExceptionMapper<StaleObjectStateException> {

  @Override
  public Response toResponse(StaleObjectStateException exception) {
    log.error("StaleObjectState exception: {}", exception.getMessage());
    return Response.status(Status.CONFLICT).build();
  }

}
