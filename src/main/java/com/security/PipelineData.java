package com.security;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class PipelineData {

    private String rootPath;
    private String encKeyCode;
    private String secretKey;
    private String publicKey;
    private String privateKey;

    public PipelineData() {
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PipelineData that = (PipelineData) o;
        return Objects.equals(rootPath, that.rootPath) && Objects.equals(encKeyCode, that.encKeyCode) && Objects.equals(secretKey, that.secretKey) && Objects.equals(publicKey, that.publicKey) && Objects.equals(privateKey, that.privateKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootPath, encKeyCode, secretKey, publicKey, privateKey);
    }

    @Override
    public String toString() {
        return "PipelineData{" +
                "rootPath='" + rootPath + '\'' +
                ", encKeyCode='" + encKeyCode + '\'' +
                ", secretKey=" + secretKey +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
