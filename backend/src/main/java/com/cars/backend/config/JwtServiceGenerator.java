package com.cars.backend.config;

import com.cars.backend.auth.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceGenerator {

    ///////////////////////////////////////////////////////
    /// Configuração para produção usando variável de ambiente
    @Value("${jwt.secret}")
    private String secretKey;

    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    public static final int TOKEN_EXPIRATION_HOURS = 1;

    ///////////////////////////////////////////////////////
    /// Configuração local
    // public static final String SECRET_KEY = "UMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUIUMACHAVESECRETADASUAAPIAQUI";

    public Map<String, Object> generatePayload(User user) {
        Map<String, Object> payloadData = new HashMap<>();
        payloadData.put("username", user.getUsername());
        payloadData.put("id", user.getId().toString());
        payloadData.put("role", user.getRole());

        return payloadData;
    }

    public String generateToken(User user) {
        Map<String, Object> payloadData = this.generatePayload(user);

        return Jwts.builder()
                .setClaims(payloadData)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000L * TOKEN_EXPIRATION_HOURS))
                .signWith(getSigningKey(), SIGNATURE_ALGORITHM)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigningKey() {
        // Para produção usa a variável de ambiente
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);

        // Para rodar localmente com valor fixo, descomente a linha abaixo:
        // byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}