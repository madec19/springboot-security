package com.man.springboot.security.filter;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if(request.getServletPath().equals("/login")) {
			filterChain.doFilter(request, response);
		} else {
			String authorization =  request.getHeader("Authorization");
			if(authorization.startsWith("Bearer ")) {
				try {
				String token = authorization.substring("Bearer ".length());
				Algorithm algo = Algorithm.HMAC256("Secret".getBytes());
				JWTVerifier verifier = JWT.require(algo).build();
				DecodedJWT decodedToken = verifier.verify(token);
				String username = decodedToken.getSubject();
				String[] roles = decodedToken.getClaim("roles").asArray(String.class);
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				
				Arrays.stream(roles).forEach(role -> {
					authorities.add(new SimpleGrantedAuthority(role));
				});
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
				}catch(Exception e) {
					log.error("Error logging in {}".concat(e.getMessage()));
					response.setHeader("error", e.getMessage());
					response.setStatus(FORBIDDEN.value());
					Map<String, String> errors = new HashMap<>();
					errors.put("error", e.getMessage());
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), errors);
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
		
	}

}
