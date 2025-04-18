package Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JWTUtil {

    // Secret key bí mật (m nên cho dài, ít nhất 256 bit nếu dùng HS256)
    private static final String SECRET_KEY = "qnity-super-secure-key-should-be-at-least-32-characters";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 1; // 1 ngày (milliseconds)
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Tạo token từ email
    public static String generateToken(String email) {
        try {
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (InvalidKeyException e) { // in rõ lỗi ra console
            // in rõ lỗi ra console
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    // Lấy email từ token
    public static String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Kiểm tra token có hợp lệ không
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
