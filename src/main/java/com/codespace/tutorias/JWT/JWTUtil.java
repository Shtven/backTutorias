package com.codespace.tutorias.JWT;

import org.springframework.stereotype.Component;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    private static final String SECRET_KEY = "AJjjasOQEPWMASDD02884weikjOWOEIIWEFDSK";

    public String generateToken(String matricula, String rol) {

        return Jwts.builder()
                .claim("matricula", matricula)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}
