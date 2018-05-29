package com.github.myutman.java;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinMD5 {

    static ForkJoinPool pool;

    static {
        pool = new ForkJoinPool();
    }

    static class MD5RecursiveTask extends RecursiveTask<byte[]> {

        File file;

        public MD5RecursiveTask(File file) {
            this.file = file;
        }

        @Override
        protected byte[] compute() {
            InputStream inputStream;
            if (file.isDirectory()) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PrintWriter writer = new PrintWriter(out);
                writer.print(file.getName());
                @NotNull File[] list = file.listFiles();
                ArrayList<ForkJoinTask<byte[]>> tasks = new ArrayList<>();
                for (File child: list) {
                    tasks.add(new MD5RecursiveTask(child).fork());
                }
                for (ForkJoinTask<byte[]> task: tasks) {
                    writer.print(new String(task.join()));
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

    @NotNull
    public static byte[] f(File file) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<byte[]> task = pool.submit(new MD5RecursiveTask(file));
        return task.join();
    }
}
