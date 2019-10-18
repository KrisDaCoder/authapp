package com.globalct.authapp.security;

import com.globalct.authapp.model.User;
import com.globalct.authapp.service.implementation.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.globalct.authapp.security.SecurityConstants.HEADER_STRING;
import static com.globalct.authapp.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenProvider tokenProvider;

  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

    try {

      String jwt = getJWTFromRequest(httpServletRequest);

      if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
        UUID id = tokenProvider.getUserIdFromJWT(jwt);
        User userDetails = customUserDetailService.loadUserById(id);
        Collection<? extends GrantedAuthority> authorities = userDetails.getRoles().stream()
                .map(role -> role.getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }

    } catch (Exception ex) {
      logger.error("Could not set user authentication in security context", ex);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);

  }

  private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(HEADER_STRING);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
      return bearerToken.substring(7, bearerToken.length());
    }

    return null;

  }

}
