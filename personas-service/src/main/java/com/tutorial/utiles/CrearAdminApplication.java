package com.tutorial.utiles;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CrearAdminApplication {

    public static void main(String[] args) {

        // Generar token JWT para un usuario ADMIN
        String token = generateJwtToken("ADMIN");

        System.out.println("Token JWT generado:");
        System.out.println(token);
    }

    private static String generateJwtToken(String username) {
        // Clave secreta para firmar el token (deberías manejarla de forma segura en un entorno real)
        String secretKey = "wIXZCWObK5oSdZriKq17XKf77UanlW/ZP/X8A7h50YPlMKcNNWIYsA+B1yJdGCHzpA02RQHqeu3JpNsI150oeQ=="; // Aquí debes usar una clave secreta fuerte y segura

        // Construir el token JWT
        return Jwts.builder()
                .setSubject(username) // Nombre de usuario como "subject" del token
                .claim("role", "ADMIN") // Claim personalizado para el rol ADMIN
                .setIssuedAt(new Date()) // Fecha de emisión del token (ahora)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de validez
                .signWith(SignatureAlgorithm.HS256, secretKey) // Firmar el token con el algoritmo HS256 y la clave secreta
                .compact(); // Compactar el token a una cadena JWT
    }
}
