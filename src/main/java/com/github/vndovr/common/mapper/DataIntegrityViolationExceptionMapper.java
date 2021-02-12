package com.github.vndovr.common.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Register classes/services/mappers specific for locations
 *
 * @author Dmitry Ruchko
 *
 */
@Provider
@Slf4j
public class DataIntegrityViolationExceptionMapper
    implements ExceptionMapper<DataIntegrityViolationException> {


  @Override public Response toResponse(DataIntegrityViolationException exception) {
        log.error("DataIntegrityViolation exception: {}", exception.getMessage());
        return Response.status(Response.Status.CONFLICT).build();
    }
}
