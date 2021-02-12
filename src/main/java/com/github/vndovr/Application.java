package com.github.vndovr;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.github.vndovr.authentication.AuthenticationResource;
import com.github.vndovr.common.mapper.ConstraintViolationExceptionMapper;
import com.github.vndovr.common.mapper.DataIntegrityViolationExceptionMapper;
import com.github.vndovr.common.mapper.FeignExceptionMapper;
import com.github.vndovr.common.mapper.ObjectOptimisticLockingFailureExceptionMapper;
import com.github.vndovr.common.mapper.StaleObjectStateExceptionMapper;
import com.github.vndovr.common.mapper.ValidationExceptionMapper;
import com.github.vndovr.user.UserResource;
import feign.Contract;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "API", version = "v1", description = "REST APIs",
        contact = @Contact(name = "Uladzimir Radchuk", email = "radchuk@hotmail.com",
            url = "https://iba.by")),
    servers = {@Server(url = "http://localhost:8080/api", description = "Local Server"),
        @Server(url = "http://sparkling-thunder-vs.icdc.io:8080/api",
            description = "Development Server on sparkling-thunder-vs.icdc.io")})
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER, scheme = "bearer")
@EnableFeignClients
@EnableCaching
public class Application extends ResourceConfig {

  /**
   * Default constructor
   */
  public Application() {
    super();
    register(OpenApiResource.class);
    register(AuthenticationResource.class);
    register(UserResource.class);

    register(ObjectMapperContextResolver.class);
    register(StaleObjectStateExceptionMapper.class);
    register(ObjectOptimisticLockingFailureExceptionMapper.class);
    register(DataIntegrityViolationExceptionMapper.class);
    register(ConstraintViolationExceptionMapper.class);
    register(ValidationExceptionMapper.class);
    register(FeignExceptionMapper.class);
  }

  @Bean
  public Contract feignContract() {
    return new JAXRSContract();
  }

  /**
   * MAIN method of the application
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * Mapping between DTO and Domain objects
   * 
   * @return
   */
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setCollectionsMergeEnabled(false);
    return modelMapper;
  }


  /**
   * JaxRS http REST client (mainly for keycloak now)
   * 
   * @return
   */
  @Bean
  public Client client() {
    return ClientBuilder
        .newClient(new ClientConfig().register(new JacksonJaxbJsonProvider(
            new ObjectMapper().configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule()),
            JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS)));
  }

}
