package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Backup {

    /**
     * Метод побитового копирования файла
     * @param fileIn - путь к исходному (копируемому) файлу
     * @param fileOut - путь к файлу назначения
     * @throws IOException - возможное исключение
     */
    private void copyFile(String fileIn, String fileOut) throws IOException {
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

    /**
     * Рекурсивный внутренний private метод резервного копирования файлов
     * @param file - исходная директория (объект типа File)
     * @param toDirPath - путь к расположению директории назначения (String)
     * @param backupDirName - имя директории назначения (String)
     * @throws IOException - возможное исключение
     */
    private void backupRecursive(File file, String toDirPath, String backupDirName) throws IOException {
        File[] files = file.listFiles();
        if (files == null)
            return;

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()){
                if (files[i].getName().equals(backupDirName))
                    continue;
                new File(toDirPath + "/" + backupDirName + "/" + files[i].getPath()).mkdirs();
                backupRecursive(files[i], toDirPath, backupDirName);
            }
            if (files[i].isFile()) {
                copyFile(files[i].toString(), toDirPath + "/" + backupDirName + files[i].getPath().substring(1));
            }
        }
    }

    /**
     * Метод резервного копирования
     *
     * @param fromDirPath   - путь к исходной директории (String)
     * @param toDirPath     - путь к директории назначения (String)
     * @param backupDirName - имя директории назначения (String)
     */
    public void backup(String fromDirPath, String toDirPath, String backupDirName) {
        try {
            new File(toDirPath + "/" + backupDirName).mkdirs();
            backupRecursive(new File(fromDirPath), toDirPath, backupDirName);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}