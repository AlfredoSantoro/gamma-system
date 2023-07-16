package com.gamma.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 * JwtUtil is responsible for creating and verifying the token
 */
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * Create jwt
     *
     * @param subject - who the token belongs to
     * @param secret - secret key to sign token
     * @param authority - list of roles
     * @return JWT
     */
    private static final String issuer = "gemma-system";

    public static String createJwt(String subject, String secret, List<String> authority) {
        logger.info("### creating JWT for subject #$subject");
        final JWTCreator.Builder builder = JWT.create()
                .withSubject(subject)
                .withIssuer(issuer)
                .withIssuedAt(Date.from(OffsetDateTime.now().toInstant()))
                .withExpiresAt(Date.from(OffsetDateTime.now().plusDays(1).toInstant()))
                .withClaim("authority", authority);
        String jwt = builder.sign(Algorithm.HMAC256(secret));
        logger.info("### jwt created successfully");
        return jwt;
    }

    /**
     * Verify jwt.
     *
     * @param jwt - token
     * @param secret - secret key to sign token
     * @return a verified and decoded JWT
     */
    public static DecodedJWT verifyJwt(String jwt, String secret) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
    }
}
