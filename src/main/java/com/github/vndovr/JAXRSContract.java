package com.github.vndovr;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;
import static feign.Util.removeValues;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import feign.DeclarativeContract;
import feign.MethodMetadata;
import feign.Request;

public class JAXRSContract extends DeclarativeContract {

  static final String ACCEPT = "Accept";
  static final String CONTENT_TYPE = "Content-Type";

  // Protected so unittest can call us
  // XXX: Should parseAndValidateMetadata(Class, Method) be public instead? The old deprecated
  // parseAndValidateMetadata(Method) was public..
  @Override
  protected MethodMetadata parseAndValidateMetadata(Class<?> targetType, Method method) {
    return super.parseAndValidateMetadata(targetType, method);
  }

  public JAXRSContract() {
    super.registerClassAnnotation(Path.class, (path, data) -> {
      if (path != null && !path.value().isEmpty()) {
        String pathValue = path.value();
        if (!pathValue.startsWith("/")) {
          pathValue = "/" + pathValue;
        }
        if (pathValue.endsWith("/")) {
          // Strip off any trailing slashes, since the template has already had slashes
          // appropriately
          // added
          pathValue = pathValue.substring(0, pathValue.length() - 1);
        }
        // jax-rs allows whitespace around the param name, as well as an optional regex. The
        // contract
        // should
        // strip these out appropriately.
        pathValue = pathValue.replaceAll("\\{\\s*(.+?)\\s*(:.+?)?\\}", "\\{$1\\}");
        data.template().uri(pathValue);
      }
    });
    super.registerClassAnnotation(Consumes.class, this::handleConsumesAnnotation);
    super.registerClassAnnotation(Produces.class, this::handleProducesAnnotation);

    registerMethodAnnotation(methodAnnotation -> {
      final Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
      final HttpMethod http = annotationType.getAnnotation(HttpMethod.class);
      return http != null;
    }, (methodAnnotation, data) -> {
      final Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
      final HttpMethod http = annotationType.getAnnotation(HttpMethod.class);
      checkState(data.template().method() == null,
          "Method %s contains multiple HTTP methods. Found: %s and %s", data.configKey(),
          data.template().method(), http.value());
      data.template().method(Request.HttpMethod.valueOf(http.value()));
    });

    super.registerMethodAnnotation(Path.class, (path, data) -> {
      final String pathValue = emptyToNull(path.value());
      if (pathValue == null) {
        return;
      }
      String methodAnnotationValue = path.value();
      if (!methodAnnotationValue.startsWith("/") && !data.template().url().endsWith("/")) {
        methodAnnotationValue = "/" + methodAnnotationValue;
      }
      // jax-rs allows whitespace around the param name, as well as an optional regex. The contract
      // should
      // strip these out appropriately.
      methodAnnotationValue =
          methodAnnotationValue.replaceAll("\\{\\s*(.+?)\\s*(:.+?)?\\}", "\\{$1\\}");
      data.template().uri(methodAnnotationValue, true);
    });
    super.registerMethodAnnotation(Consumes.class, this::handleConsumesAnnotation);
    super.registerMethodAnnotation(Produces.class, this::handleProducesAnnotation);

    // trying to minimize the diff
    registerParamAnnotations();
  }

  private void handleProducesAnnotation(Produces produces, MethodMetadata data) {
    final String[] serverProduces =
        removeValues(produces.value(), mediaType -> emptyToNull(mediaType) == null, String.class);
    checkState(serverProduces.length > 0, "Produces.value() was empty on %s", data.configKey());
    data.template().header(ACCEPT, Collections.emptyList()); // remove any previous produces
    data.template().header(ACCEPT, serverProduces);
  }

  private void handleConsumesAnnotation(Consumes consumes, MethodMetadata data) {
    final String[] serverConsumes =
        removeValues(consumes.value(), mediaType -> emptyToNull(mediaType) == null, String.class);
    checkState(serverConsumes.length > 0, "Consumes.value() was empty on %s", data.configKey());
    data.template().header(CONTENT_TYPE, serverConsumes);
  }

  protected void registerParamAnnotations() {
    {
      registerParameterAnnotation(PathParam.class, (param, data, paramIndex) -> {
        final String name = param.value();
        checkState(emptyToNull(name) != null, "PathParam.value() was empty on parameter %s",
            paramIndex);
        nameParam(data, name, paramIndex);
      });
      registerParameterAnnotation(QueryParam.class, (param, data, paramIndex) -> {
        final String name = param.value();
        checkState(emptyToNull(name) != null, "QueryParam.value() was empty on parameter %s",
            paramIndex);
        final String query = addTemplatedParam(name);
        data.template().query(name, query);
        nameParam(data, name, paramIndex);
      });
      registerParameterAnnotation(HeaderParam.class, (param, data, paramIndex) -> {
        final String name = param.value();
        checkState(emptyToNull(name) != null, "HeaderParam.value() was empty on parameter %s",
            paramIndex);
        final String header = addTemplatedParam(name);
        data.template().header(name, header);
        nameParam(data, name, paramIndex);
      });
      registerParameterAnnotation(FormParam.class, (param, data, paramIndex) -> {
        final String name = param.value();
        checkState(emptyToNull(name) != null, "FormParam.value() was empty on parameter %s",
            paramIndex);
        data.formParams().add(name);
        nameParam(data, name, paramIndex);
      });
    }
  }

  // Not using override as the super-type's method is deprecated and will be removed.
  private String addTemplatedParam(String name) {
    return String.format("{%s}", name);
  }
}
