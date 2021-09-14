package com.wangd.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangd
 */
public class TokenUtils {

    public static final long EXPIRE_TIME = 1 * 60 * 24 * 1000;

    public static final String TOKEN_SECRET = "dfe446abf7ce4bd391f2b7c45046b020";

    public static String sign(String username, String userId){

        // 过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "SH256");
        return "Bearer " + JWT.create().withHeader(header).withClaim("loginName", username)
                .withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }

    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token.replace("Bearer ", ""));
            return true;
        }catch (Exception exception){
            return false;
        }

    }
}
