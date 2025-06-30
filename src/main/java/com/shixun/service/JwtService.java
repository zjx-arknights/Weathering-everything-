package com.shixun.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;

public class JwtService {
    public String generateJwt() {
        try {
            // Private key
            String privateKeyString = "MC4CAQAwBQYDK2VwBCIEINHxJA2iIJstUj7mV4hRGcg2nwT4C/sL2muvpfUdAqKV";

            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EdDSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Header
            String headerJson = "{\"alg\": \"EdDSA\", \"kid\": \"TFB4NM7UQB\"}";

            // Payload
            long iat = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() - 30;
            long exp = iat + 900;
            String payloadJson = "{\"sub\": \"39TNF379TU\", \"iat\": " + iat + ", \"exp\": " + exp + "}";

            // Base64url header+payload
            String headerEncoded = Base64.getUrlEncoder().encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
            String payloadEncoded = Base64.getUrlEncoder().encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
            String data = headerEncoded + "." + payloadEncoded;

            // Sign
            Signature signer = Signature.getInstance("EdDSA");
            signer.initSign(privateKey);
            signer.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signature = signer.sign();

            String signatureEncoded = Base64.getUrlEncoder().encodeToString(signature);
            return data + "." + signatureEncoded;
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT", e);
        }
    }
}