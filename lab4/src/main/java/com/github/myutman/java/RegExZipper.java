package com.github.myutman.java;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by myutman on 10/9/17.
 *
 * Console application that extracts archives in given directory that matches given regular expression.
 */
public class RegExZipper {

    /**
     * Unzips all the files that match pattern from all the archives in the given directory.
     * @param args 1) name of directory, 2) pattern
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            throw new IllegalArgumentException();
        }

        String dirName = args[0];
        String pattern = args[1];
        File dir = new File(dirName);

        File[] files = dir.listFiles();
        if (files == null){
            throw new FileNotFoundException();
        }

        for (File file : files) {
            if (file.isDirectory()) continue;
            try (ZipFile zipFile = new ZipFile(file)) {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()){
                    ZipEntry zipEntry = entries.nextElement();
                    if (zipEntry.getName().matches(pattern)) {
                        if (zipEntry.isDirectory()) {
                            File newFile = new File(dir.getAbsolutePath() + "/" + zipEntry.getName());
                            newFile.mkdir();
                            continue;
                        }
                        File newFile = new File(dir.getAbsolutePath() + "/" + zipEntry.getName());
                        if (!newFile.getParentFile().exists()) {
                            newFile.getParentFile().mkdirs();
                        }
                        try (FileOutputStream out = new FileOutputStream(newFile)) {
                            BufferedInputStream in = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                            while (in.available() > 0) {
                                byte[] bytes = new byte[in.available()];
                                in.read(bytes);
                                out.write(bytes);
                            }
                        }
                    }
                }
            }
        }
    }
}
