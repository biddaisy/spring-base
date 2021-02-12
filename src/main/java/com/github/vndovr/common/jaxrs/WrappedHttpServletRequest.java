package com.github.vndovr.common.jaxrs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WrappedHttpServletRequest implements HttpServletRequest {
  private HttpServletRequest delegate;

  @Override
  public Object getAttribute(String name) {
    return delegate.getAttribute(name);
  }

  @Override
  public Enumeration<String> getAttributeNames() {
    return delegate.getAttributeNames();
  }

  @Override
  public String getCharacterEncoding() {
    return delegate.getCharacterEncoding();
  }

  @Override
  public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
    delegate.setCharacterEncoding(env);
  }

  @Override
  public int getContentLength() {
    return delegate.getContentLength();
  }

  @Override
  public long getContentLengthLong() {
    return delegate.getContentLengthLong();
  }

  @Override
  public String getContentType() {
    return delegate.getContentType();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    return delegate.getInputStream();
  }

  @Override
  public String getParameter(String name) {
    return delegate.getParameter(name);
  }

  @Override
  public Enumeration<String> getParameterNames() {
    return delegate.getParameterNames();
  }

  @Override
  public String[] getParameterValues(String name) {
    return delegate.getParameterValues(name);
  }

  @Override
  public Map<String, String[]> getParameterMap() {
    return delegate.getParameterMap();
  }

  @Override
  public String getProtocol() {
    return delegate.getProtocol();
  }

  @Override
  public String getScheme() {
    return delegate.getScheme();
  }

  @Override
  public String getServerName() {
    return delegate.getServerName();
  }

  @Override
  public int getServerPort() {
    return delegate.getServerPort();
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return delegate.getReader();
  }

  @Override
  public String getRemoteAddr() {
    return delegate.getRemoteAddr();
  }

  @Override
  public String getRemoteHost() {
    return delegate.getRemoteHost();
  }

  @Override
  public void setAttribute(String name, Object o) {
    delegate.setAttribute(name, o);
  }

  @Override
  public void removeAttribute(String name) {
    delegate.removeAttribute(name);
  }

  @Override
  public Locale getLocale() {
    return delegate.getLocale();
  }

  @Override
  public Enumeration<Locale> getLocales() {
    return delegate.getLocales();
  }

  @Override
  public boolean isSecure() {
    return delegate.isSecure();
  }

  @Override
  public RequestDispatcher getRequestDispatcher(String path) {
    return delegate.getRequestDispatcher(path);
  }

  @SuppressWarnings("deprecation")
  @Override
  public String getRealPath(String path) {
    return delegate.getRealPath(path);
  }

  @Override
  public int getRemotePort() {
    return delegate.getRemotePort();
  }

  @Override
  public String getLocalName() {
    return delegate.getLocalName();
  }

  @Override
  public String getLocalAddr() {
    return delegate.getLocalAddr();
  }

  @Override
  public int getLocalPort() {
    return delegate.getLocalPort();
  }

  @Override
  public ServletContext getServletContext() {
    return delegate.getServletContext();
  }

  @Override
  public AsyncContext startAsync() {
    return delegate.startAsync();
  }

  @Override
  public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
    return delegate.startAsync(servletRequest, servletResponse);
  }

  @Override
  public boolean isAsyncStarted() {
    return delegate.isAsyncStarted();
  }

  @Override
  public boolean isAsyncSupported() {
    return delegate.isAsyncSupported();
  }

  @Override
  public AsyncContext getAsyncContext() {
    return delegate.getAsyncContext();
  }

  @Override
  public DispatcherType getDispatcherType() {
    return delegate.getDispatcherType();
  }

  @Override
  public String getAuthType() {
    return delegate.getAuthType();
  }

  @Override
  public Cookie[] getCookies() {
    return delegate.getCookies();
  }

  @Override
  public long getDateHeader(String name) {
    return delegate.getDateHeader(name);
  }

  @Override
  public String getHeader(String name) {
    return name.equalsIgnoreCase("origin") ? null : delegate.getHeader(name);
  }

  @Override
  public Enumeration<String> getHeaders(String name) {
    return name.equalsIgnoreCase("origin") ? new Enumeration<String>() {

      @Override
      public boolean hasMoreElements() {
        return false;
      }

      @Override
      public String nextElement() {
        return null;
      }
    } : delegate.getHeaders(name);
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    return delegate.getHeaderNames();
  }

  @Override
  public int getIntHeader(String name) {
    return delegate.getIntHeader(name);
  }

  @Override
  public String getMethod() {
    return delegate.getMethod();
  }

  @Override
  public String getPathInfo() {
    return delegate.getPathInfo();
  }

  @Override
  public String getPathTranslated() {
    return delegate.getPathTranslated();
  }

  @Override
  public String getContextPath() {
    return delegate.getContextPath();
  }

  @Override
  public String getQueryString() {
    return delegate.getQueryString();
  }

  @Override
  public String getRemoteUser() {
    return delegate.getRemoteUser();
  }

  @Override
  public boolean isUserInRole(String role) {
    return delegate.isUserInRole(role);
  }

  @Override
  public Principal getUserPrincipal() {
    return delegate.getUserPrincipal();
  }

  @Override
  public String getRequestedSessionId() {
    return delegate.getRequestedSessionId();
  }

  @Override
  public String getRequestURI() {
    return delegate.getRequestURI();
  }

  @Override
  public StringBuffer getRequestURL() {
    return delegate.getRequestURL();
  }

  @Override
  public String getServletPath() {
    return delegate.getServletPath();
  }

  @Override
  public HttpSession getSession(boolean create) {
    return delegate.getSession(create);
  }

  @Override
  public HttpSession getSession() {
    return delegate.getSession();
  }

  @Override
  public String changeSessionId() {
    return delegate.changeSessionId();
  }

  @Override
  public boolean isRequestedSessionIdValid() {
    return delegate.isRequestedSessionIdValid();
  }

  @Override
  public boolean isRequestedSessionIdFromCookie() {
    return delegate.isRequestedSessionIdFromCookie();
  }

  @Override
  public boolean isRequestedSessionIdFromURL() {
    return delegate.isRequestedSessionIdFromURL();
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isRequestedSessionIdFromUrl() {
    return delegate.isRequestedSessionIdFromUrl();
  }

  @Override
  public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
    return delegate.authenticate(response);
  }

  @Override
  public void login(String username, String password) throws ServletException {
    delegate.login(username, password);
  }

  @Override
  public void logout() throws ServletException {
    delegate.logout();
  }

  @Override
  public Collection<Part> getParts() throws IOException, ServletException {
    return delegate.getParts();
  }

  @Override
  public Part getPart(String name) throws IOException, ServletException {
    return delegate.getPart(name);
  }

  @Override
  public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass)
      throws IOException, ServletException {
    return delegate.upgrade(httpUpgradeHandlerClass);
  }
}
