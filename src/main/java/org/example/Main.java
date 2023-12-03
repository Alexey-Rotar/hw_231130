package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        backup(".", ".", "backup");
    }

    public static void createDir(String path){
        File dir = new File(path);
        dir.mkdirs();
    }

    public static void copyFile(String fileIn, String fileOut) throws IOException {
        // На запись
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileOut)){
            int c;
            // На чтение
            try (FileInputStream fileInputStream = new FileInputStream(fileIn)){
                while ((c = fileInputStream.read()) != -1){
                    fileOutputStream.write(c);
                }
            }
        }
    }

    public static void backupRecursive(File file, String backupDirName) throws IOException {
        File[] files = file.listFiles();
        if (files == null)
            return;

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()){
                if (files[i].getName().equals(backupDirName))
                    continue;
                createDir(".//backup/" + files[i].getPath());
                backupRecursive(files[i], backupDirName);
            }
            if (files[i].isFile()) {
                copyFile(files[i].toString(), ".//backup" + files[i].getPath().substring(1));
            }
        }
    }

    public static void backup(String fromDirPath, String toDirPath, String backupDirName) throws IOException {
        createDir(toDirPath + "/" + backupDirName);
        backupRecursive(new File(fromDirPath), backupDirName);
    }
}