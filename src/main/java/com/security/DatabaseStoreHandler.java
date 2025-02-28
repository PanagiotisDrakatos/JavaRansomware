package com.security;

import lombok.SneakyThrows;

public class DatabaseStoreHandler implements Handler<PipelineData, PipelineData> {

    public DatabaseStoreHandler() {
    }

    @SneakyThrows
    @Override
    public PipelineData process(PipelineData input) {
        String encryptedSymmetricKey = DerbyStorage.getInstance().retrieveString();

        if (encryptedSymmetricKey == null) {
            // Store a new string
            DerbyStorage.getInstance().storeString(input.getEncKeyCode());
            System.out.println("Retrieved newly stored string: " + input.getEncKeyCode());
        }
        return input;
    }
}
