package com.github.vndovr.common.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

/**
 * Indicates that entity was not found in the persistence storage
 * 
 * @author Vladimir Radchuk
 *
 */
public class ObjectNotFoundException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public ObjectNotFoundException() {
    super(Status.NOT_FOUND);
  }
}
