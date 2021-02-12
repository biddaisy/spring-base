package com.github.vndovr.common.jaxrs;

/**
 * Standard descriptions for some http status codes
 * 
 * @author Vladimir Radchuk
 *
 */
public interface Descriptions {

  public static final String D200 = "The request has been successfully applied ";

  public static final String D400 =
      "The request has not been applied because of invalid input parameters.";

  public static final String D401 =
      "The request has not been applied because it lacks valid authentication credentials for the target resource.";

  public static final String D403 =
      "The request is understood, but it has been refused or access is not allowed. 1) The code is returned in case of an issue exception. Please see RestIssueLog in the body 2) The user is suspended. The message in the body exists.";

  public static final String D404 =
      "The URI requested is invalid or the resource requested, does not exist. Also returned when the requested format is not supported by the requested method.";

  public static final String D500 =
      "Internal Server Error. The service call has not succeeded. The string in the body may contain the details.";

}
