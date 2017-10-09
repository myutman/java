package com.github.myutman.java;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by myutman on 10/9/17.
 */
public class RegExZipper {

    /**
     * @param args - arg[0] - name of directory, arg[1] - pattern
     * @throws IOException
     *
     * Unzips all the files that match pattern from all the archives in the given directory.
     */
    public static void main(String[] args) throws IOException {
        String dirName = args[0];
        String pattern = args[1];
        File dir = new File(dirName);

        for (File file : dir.listFiles()){
            if (file.isDirectory()) continue;
            try (ZipFile zipFile = new ZipFile(file)){
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()){
                    ZipEntry zipEntry = entries.nextElement();
                    if (zipEntry.getName().matches(pattern)){
                        if (zipEntry.isDirectory()){
                            File newFile = new File (dir.getAbsolutePath() + "/" + zipEntry.getName());
                            newFile.mkdir();
                            continue;
                        }
                        File newFile = new File (dir.getAbsolutePath() + "/" + zipEntry.getName());
                        if (!newFile.getParentFile().exists()){
                            newFile.getParentFile().mkdirs();
                        }
                        FileOutputStream out = new FileOutputStream(newFile);
                        BufferedInputStream in = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                        while (in.available() > 0){
                            byte[] bytes = new byte[in.available()];
                            in.read(bytes);
                            out.write(bytes);
                        }
                        out.close();
                    }
                }
            } catch (ZipException zp){ }
        }
    }
}
