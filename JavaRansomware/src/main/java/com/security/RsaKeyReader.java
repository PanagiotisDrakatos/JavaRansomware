package com.security;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class RsaKeyReader {

    private static final String Algorithm = "RSA";

    public static PublicKey loadPublicKey(String stored) throws GeneralSecurityException {
        byte[] data = Base64.decodeBase64(stored);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance(Algorithm);
        return fact.generatePublic(spec);
    }

    public static PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = Base64.decodeBase64(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance(Algorithm);
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    public static PrivateKey loadPrivateKey(File PrivFile) throws Exception {
        byte[] keyBytes = Files.readAllBytes(PrivFile.toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(Algorithm);
        return kf.generatePrivate(spec);
    }

    public static PublicKey loadPublicKey(File PubFile) throws Exception {
        byte[] keyBytes = Files.readAllBytes(PubFile.toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(Algorithm);
        return kf.generatePublic(spec);
    }
}
