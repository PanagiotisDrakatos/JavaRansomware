package com.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;

public class AESCipher {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256; // AES-256
    private static final int IV_LENGTH = 12; // 12 bytes for GCM
    private static final int TAG_LENGTH = 128; // 128-bit authentication tag


    // Generate AES key not save to file
    public static String generateAndNotSaveKeyAsString() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEY_SIZE);
        SecretKey key = keyGen.generateKey();

        // Save key as Base64 string
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // Generate AES key not save to file
    public static SecretKey generateAndNotSaveKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }

    // Generate AES key and save to file
    public static SecretKey generateAndSaveKey(String keyFile) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(KEY_SIZE);
        SecretKey key = keyGen.generateKey();

        // Save key as Base64 string
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        Files.write(Path.of(keyFile), encodedKey.getBytes());
        return key;
    }

    // Load key from string
    public static SecretKey loadKey(String encodedKey) throws IOException {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "AES");
    }

    // Load key from file
    public static SecretKey loadKeyFormFile(String keyFile) throws IOException {
        byte[] encodedKey = Files.readAllBytes(Path.of(keyFile));
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "AES");
    }

    // Encrypt file and replace original
    public static void encryptFile(String filePath, SecretKey key) throws Exception {
        // Read original file
        byte[] fileData = Files.readAllBytes(Path.of(filePath));

        // Generate IV
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);

        // Initialize cipher
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));

        // Encrypt data
        byte[] encryptedData = cipher.doFinal(fileData);

        // Write IV + encrypted data back to the same file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(iv);
            fos.write(encryptedData);
        }
    }

    // Decrypt file and replace encrypted version
    public static void decryptFile(String filePath, SecretKey key) throws Exception {
        // Read encrypted file (IV + ciphertext)
        byte[] encryptedFile = Files.readAllBytes(Path.of(filePath));

        // Extract IV (first 12 bytes)
        byte[] iv = new byte[IV_LENGTH];
        System.arraycopy(encryptedFile, 0, iv, 0, IV_LENGTH);

        // Extract ciphertext (remaining bytes)
        byte[] ciphertext = new byte[encryptedFile.length - IV_LENGTH];
        System.arraycopy(encryptedFile, IV_LENGTH, ciphertext, 0, ciphertext.length);

        // Initialize cipher
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));

        // Decrypt data
        byte[] decryptedData = cipher.doFinal(ciphertext);

        // Write decrypted data back to the same file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decryptedData);
        }
    }
}
