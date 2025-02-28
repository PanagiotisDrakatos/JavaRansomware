package com.security;

import lombok.SneakyThrows;

public class DatabaseRetrieveHandler implements Handler<PipelineData, PipelineData> {

    public DatabaseRetrieveHandler() {
    }

    @SneakyThrows
    @Override
    public PipelineData process(PipelineData input) {
        String encryptedSymmetricKey = DerbyStorage.getInstance().retrieveString();

        if (encryptedSymmetricKey != null) {
            System.out.println("Retrieved existing string: " + encryptedSymmetricKey);
            input.setEncKeyCode(encryptedSymmetricKey);
        }
        return input;
    }
}
