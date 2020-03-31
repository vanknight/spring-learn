package com.learn.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {
    // 5 min
    private static final long EXPIRE_TIME = 5*60*1000;
    public static String sign(String username,String secret){
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        Algorithm algorithm = encryption(secret);
        return JWT.create()
                .withClaim("username",username)
                .withExpiresAt(date)
                .sign(algorithm);
    }
    public static boolean verify(String token,String username,String secret) {
        Algorithm algorithm = encryption(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username",username)
                .build();
        try {
            DecodedJWT jwt = verifier.verify(token);
        }catch (TokenExpiredException e){
            System.out.println("Token过期或者不存在");
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;

    }
    public static String getUsername(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }
    private static Algorithm encryption(String secret){
        return Algorithm.HMAC256(secret);
    }
}
