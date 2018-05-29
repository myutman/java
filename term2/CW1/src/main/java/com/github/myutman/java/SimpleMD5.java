package com.github.myutman.java;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SimpleMD5 {

    @NotNull
    public static byte[] f(File file) {
        InputStream inputStream;
        if (file.isDirectory()) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(out);
            writer.print(file.getName());
            @NotNull File[] list = file.listFiles();
            for (File child: list) {
                @NotNull byte[] result = f(child);
                writer.print(new String(result));
            }
            writer.flush();
            byte[] bytes = out.toByteArray();
            inputStream = new ByteArrayInputStream(bytes);
        } else {
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                return new byte[]{};
            }
        }
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return new byte[]{};
        }
        try (DigestInputStream digestInputStream = new DigestInputStream(inputStream, md)) {
            byte[] buffer = new byte[1024];
            while (digestInputStream.available() > 0) {
                int size = digestInputStream.read(buffer);
            }
        } catch (IOException e) {
            return new byte[]{};
        }
        return md.digest();
    }
}
