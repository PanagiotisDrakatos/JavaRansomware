package com.security;

import lombok.SneakyThrows;

public class GenSymmetricKeyHandler implements Handler<PipelineData, PipelineData> {
    @Override
    @SneakyThrows
    public PipelineData process(PipelineData input) {
        if (input.getEncKeyCode() != null) {
            return input;
        }
        input.setSecretKey(AESCipher.generateAndNotSaveKeyAsString());
        return input;
    }
}
