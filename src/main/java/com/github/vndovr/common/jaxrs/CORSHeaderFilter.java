package com.github.vndovr.common.jaxrs;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CORSHeaderFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
    httpServletResponse.setHeader("Access-Control-Max-Age", "1209600");
    chain.doFilter(new WrappedHttpServletRequest((HttpServletRequest) request), response);
  }

}
