package com.example.webCommon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JwtUtils {
    private static final String SECRET_KEY = "qwertyuiopasdfghjklzxcvbnm";
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;

    /**
     * 生成加密后的秘钥 secretKey
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static String createJWT(String subject) {
        return createJWT(subject, Map.of());
    }

    /**
     * 生成JWT的令牌
     * @param subject token的所有者，可以存放用户id
     * @param payload 负载
     * @return 令牌
     */
    public static String createJWT(String subject, Map<String, Object> payload) {
        return Jwts.builder()
				// 设置jwt的头部信息，包括类型和算法名
				.setHeaderParam("typ", "jwt")
				.setHeaderParam("alg", ALGORITHM.getValue())
				// 负载
				.setId(UUID.randomUUID().toString())
				.setSubject(subject)
		        .addClaims(payload)
				.setExpiration(new Date(Long.MAX_VALUE)) // 暂不考虑过期时间，因为过期由redis控制
				// 签名
				.signWith(ALGORITHM, generalKey())
				// 使用.将三部分拼接起来
				.compact();
    }

    public static Claims parseJWT(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
