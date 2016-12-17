package com.security;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;

public class Serializer {

    public static byte[] Serialize(final TreeMap<String, HashMap<String, String>> map) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(map);
        oos.close();
        return baos.toByteArray();
    }


    public static TreeMap<String, HashMap<String, String>> Deserialize(InputStream stream) throws Exception {

        ObjectInputStream ois = new ObjectInputStream(stream);
        Object obj = ois.readObject();

        final ObjectMapper oMapper = new ObjectMapper();
        final TreeMap<String, HashMap<String, String>> Map = oMapper.convertValue(obj, TreeMap.class);

        return Map;
    }
}
