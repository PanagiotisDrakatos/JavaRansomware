package com.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.*;
import java.util.Base64;
import java.util.Map;
import java.util.Set;

public class RSACipher {

    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final int KEY_SIZE = 256; // AES-256
    private static final int IV_LENGTH = 12; // 12 bytes for GCM
    private static final int TAG_LENGTH = 128; // 128-bit authentication tag

    private static volatile RSACipher instance;
    private static String prov;

    private RSACipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        // to prevent instantiating by Reflection call
        if (instance != null) {
            throw new IllegalStateException("Already initialized.");
        }
        Security.addProvider(new BouncyCastleProvider());
        Init();
    }

    public static RSACipher getInstance() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        var result = instance;
        if (result == null) {
            synchronized (RSACipher.class) {
                result = instance;
                if (result == null) {
                    result = new RSACipher();
                    instance = result;
                }
            }
        }
        return result;
    }

    private void Init() {
        for (Provider provider : Security.getProviders()) {
            //System.out.println("Provider: " + provider.getName());
            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                if ("Cipher".equals(service.getType())) {
                    if (service.getAlgorithm().equals(ALGORITHM)) {
                        //System.out.println(" - " + service.getAlgorithm());
                        prov = provider.getName();
                        break;
                    }
                }
            }
        }
    }

    public String encrypt(byte[] data, PublicKey publicKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data));
    }

    public String decrypt(String encrypted, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] data = Base64.getDecoder().decode(encrypted);
        return new String(cipher.doFinal(data));
    }

    private static void removeCryptographyRestrictions() {
        if (!isRestrictedCryptography()) {
            System.out.println("Cryptography restrictions removal not needed");
            return;
        }
        try {

            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

            System.out.println("Successfully removed cryptography restrictions");
        } catch (final Exception e) {
            System.out.println("Failed to remove cryptography restrictions" + e);
        }
    }

    private static boolean isRestrictedCryptography() {
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }
}
