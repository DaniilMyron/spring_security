package com.miron.security_lib.old;

import com.miron.security_lib.models.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.UUID;
import java.util.function.Function;

public class AccessTokenJwsStringDeserializer implements Function<String, Token> {
    private final JWSVerifier jwsVerifier;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenJwsStringDeserializer.class);

    public AccessTokenJwsStringDeserializer(JWSVerifier jwsVerifier) {
        this.jwsVerifier = jwsVerifier;
    }

    @Override
    public Token apply(String string) {
        try {
            var signedJWT = SignedJWT.parse(string);
            if(signedJWT.verify(jwsVerifier)){
                var claimsSet = signedJWT.getJWTClaimsSet();
                return new Token(UUID.fromString(claimsSet.getJWTID()),
                        claimsSet.getSubject(),
                        claimsSet.getStringListClaim("authorities"),
                        claimsSet.getIssueTime().toInstant(),
                        claimsSet.getExpirationTime().toInstant());
            }
        } catch (ParseException | JOSEException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
