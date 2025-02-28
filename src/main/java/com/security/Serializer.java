package com.security;


import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.fury.Fury;
import org.apache.fury.ThreadSafeFury;
import org.apache.fury.config.Language;

@Getter
public class Serializer {

    private static volatile Serializer instance;

    private final ThreadSafeFury fury;

    private Serializer() throws ClassNotFoundException {
        if (instance != null) {
            throw new IllegalStateException("Already initialized.");
        }
        this.fury = Fury.builder()
                .withLanguage(Language.JAVA)
                .withRefTracking(false)
                .withRefCopy(false)
                .withLongCompressed(true)
                .withIntCompressed(true)
                .withStringCompressed(true)
                .withScalaOptimizationEnabled(true)
                .withClassVersionCheck(false)
                .withAsyncCompilation(false)
                .withCodegen(false)
                .requireClassRegistration(false)
                .buildThreadSafeFury();
    }

    @SneakyThrows
    public static Serializer getInstance() {

        Serializer result = instance;
        if (result == null) {
            synchronized (Serializer.class) {
                result = instance;
                if (result == null) {
                    result = new Serializer();
                    instance = result;
                }
            }
        }
        return result;
    }
}
