package org.example;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        Backup b = new Backup();
        b.backup(".", ".", "backup");
    }
}