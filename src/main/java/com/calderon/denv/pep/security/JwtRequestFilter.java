package com.calderon.denv.pep.security;

import com.calderon.denv.pep.service.auth.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    String userId = null;
    String jwt = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
      try {
        userId = jwtUtil.extractSubject(jwt);
      } catch (Exception e) {
        logger.error("Token JWT inválido o no se pudo extraer el email");
      }
    }

    // If the token is valid and has not previously authenticated, we proceed to load the user details
    if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var userDetails = userService.loadUser(Long.valueOf(userId));
      if (jwtUtil.isTokenValid(jwt, userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
      }
    }
    chain.doFilter(request, response);
  }
}
