package com.ritesh.dineflow.configuration.security;

import com.ritesh.dineflow.exceptions.UnauthorizedAccessException;
import com.ritesh.dineflow.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}") // 1 hour in milliseconds
	private long validityInMilliseconds;

	@Autowired
	private UserService userService;

	private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

	@PostConstruct
	protected void init() {
		// Encode the secret key for added security
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	// Generate JWT Token for a given user
	public String generateToken(Authentication authentication, Claims extraClaims) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuedAt(now)
				.claims(extraClaims)
				.expiration(validity)
				.signWith(getSignInKey()).compact();
	}

	public Authentication getAuthentication(String token) {
		// Extract username from token
		String username = getUsername(token);

		// Load user details based on username
		UserDetails userDetails = userService.findByUsername(username).orElseThrow(() -> new UnauthorizedAccessException("User Not Authorised"));
		// Return an Authentication object
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}


	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	@SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
		try {
			return Jwts
					.parser()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(token)
					.getPayload();
		} catch (Exception e) {
			LOGGER.info("Exception at extractAllClaims {}", e.getMessage());
			throw new UnauthorizedAccessException("User Not Authorise");
		}

	}

	// Extract token from Authorization header
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
