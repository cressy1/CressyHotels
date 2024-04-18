package com.kingslandinghotelandsuites.kingslandinghotelandsuites.security.jwt;

import com.kingslandinghotelandsuites.kingslandinghotelandsuites.security.user.HotelUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

import static jdk.internal.org.jline.keymap.KeyMap.key;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.jwtExpirationInMils}")
    private int jwtExpirationTime;

    public String generateJwtTokenForUser(Authentication authentication) {
        HotelUserDetails userPrinciples = (HotelUserDetails) authentication.getPrincipal();
        List<String> roles = userPrinciples.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userPrinciples.getUsername())
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationTime))
                .signWith(key(), SignatureAlgorithm.HS256).compact();

    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromToken(String tokeb) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(tokeb).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid jwt token : {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("This token has expired : {} ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("This token is not supported : {} ", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("No claims found", e.getMessage());
        }
        return false;

    }


}
