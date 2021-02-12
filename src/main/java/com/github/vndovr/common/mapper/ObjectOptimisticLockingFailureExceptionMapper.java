package com.github.vndovr.common.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import lombok.extern.slf4j.Slf4j;

/**
 * Indicates conflict during persistence update
 * 
 * @author Vladimir Radchuk
 *
 */
@Provider
@Slf4j
public class ObjectOptimisticLockingFailureExceptionMapper
    implements ExceptionMapper<ObjectOptimisticLockingFailureException> {

  @Override
  public Response toResponse(ObjectOptimisticLockingFailureException exception) {
    log.error("ObjectOptimisticLockingFailure exception: {}", exception.getMessage());
    return Response.status(Status.CONFLICT).build();
  }

}
