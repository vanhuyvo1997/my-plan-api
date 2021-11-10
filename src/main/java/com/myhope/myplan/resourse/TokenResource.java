package com.myhope.myplan.resourse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhope.myplan.domain.PlanUser;
import com.myhope.myplan.domain.PlanUserRole;
import com.myhope.myplan.service.userservice.PlanUserService;

@RestController
@RequestMapping("/api/token/refresh")
public class TokenResource {

	private final Environment env;

	private final PlanUserService planUserService;

	public TokenResource(Environment env, PlanUserService planUserService) {
		this.env = env;
		this.planUserService = planUserService;
	}

	@PostMapping
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				Algorithm algorithm = Algorithm.HMAC256(env.getProperty("myplan.secetkey"));
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedToken = verifier.verify(refresh_token);
				String username = decodedToken.getSubject();
				PlanUser user = planUserService.getUser(username);

				String access_token = JWT.create().withSubject(user.getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 10000))
						.withIssuer(request.getRequestURL().toString())
						.withClaim("roles", user.getPlanUserRoles()
								.stream().map(PlanUserRole::getName).collect(Collectors.toList()))
						.sign(algorithm);
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", access_token);
				tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch (Exception e) {
				response.setHeader("error", e.getMessage());
				// response.sendError(HttpStatus.FORBIDDEN.value());
				response.setStatus(HttpStatus.FORBIDDEN.value());
				var error = new HashMap<String, String>();
				error.put("error_message", e.getMessage());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("Error refresh token");
		}
	}
}
