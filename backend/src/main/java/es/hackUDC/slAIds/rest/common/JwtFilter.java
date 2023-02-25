package es.hackUDC.slAIds.rest.common;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtFilter extends BasicAuthenticationFilter {

  private JwtGenerator jwtGenerator;

  public JwtFilter(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {

    super(authenticationManager);

    this.jwtGenerator = jwtGenerator;

  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

    System.out.println("authHeaderValue: " + authHeaderValue);

    if (authHeaderValue == null || !authHeaderValue.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {

      String serviceToken = authHeaderValue.replace("Bearer ", "");

      Claims claims = Jwts.parser()
          .setSigningKey("Bu:GW8bgPlEw".getBytes())
          .parseClaimsJws(serviceToken)
          .getBody();

      JwtInfo jwtInfo = new JwtInfo(
          ((Integer) claims.get("userId")).longValue(),
          claims.getSubject(),
          (String) claims.get("role"));

      request.setAttribute("serviceToken", serviceToken);
      request.setAttribute("userId", jwtInfo.getUserId());

      configureSecurityContext(jwtInfo.getUserName(), jwtInfo.getRole());

    } catch (Exception e) {
      System.out.println("EXCEPTION: " + e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);

  }

  private void configureSecurityContext(String userName, String role) {

    Set<GrantedAuthority> authorities = new HashSet<>();

    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(userName, null, authorities));

  }

}
