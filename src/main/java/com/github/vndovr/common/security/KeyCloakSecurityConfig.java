package com.github.vndovr.common.security;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.github.vndovr.Profiles;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
@KeycloakConfiguration
@Profile(Profiles.STAGING)
class KeyCloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  @Value("${spring.h2.console.enabled:false}")
  private boolean h2ConsoleEnabled;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        keycloakAuthenticationProvider();
    SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
    mapper.setPrefix("");
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(mapper);
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Bean
  @Primary
  public KeycloakConfigResolver keycloakConfigResolver(KeycloakSpringBootProperties properties,
      KeyCloakSecuritySettings config) {
    return new KeyCloakConfigResolver(properties, config);
  }

  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    (h2ConsoleEnabled ? http.headers().frameOptions().disable().and() : http).cors().and().csrf()
        .disable().authorizeRequests()
        .antMatchers("/api/login", "/api/refresh", "/api/logout", "/api/openapi.json").permitAll()
        .antMatchers("/api/**").authenticated().anyRequest().permitAll();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration
        .setAllowedMethods(Stream.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .collect(Collectors.toList()));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(
        Stream.of("Authorization", "Cache-Control", "Content-Type").collect(Collectors.toList()));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
