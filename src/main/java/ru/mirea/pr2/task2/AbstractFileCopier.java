package ru.mirea.pr2.task2;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractFileCopier {
    protected final String sourceFile = "src/main/resources/pr2/source.txt";
    protected final String destinationFile = "src/main/resources/pr2/destination.txt";

    public AbstractFileCopier() throws IOException {
        createLargeFile(sourceFile, 100);
    }

    private static void createLargeFile(String fileName, int sizeInMB) throws
            IOException {
        byte[] data = new byte[1024 * 1024];
        FileOutputStream fos = new FileOutputStream(fileName);
        for (int i = 0; i < sizeInMB; i++) {
            fos.write(data);
        }
        fos.close();
    }

    abstract void copyFile() throws IOException;
}
