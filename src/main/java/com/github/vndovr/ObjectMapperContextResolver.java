package com.github.vndovr;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
  private final ObjectMapper objectMapper;

  public ObjectMapperContextResolver() {
    objectMapper = new ObjectMapper().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());
  }

  @Override
  public ObjectMapper getContext(Class<?> type) {
    return objectMapper;
  }

}
