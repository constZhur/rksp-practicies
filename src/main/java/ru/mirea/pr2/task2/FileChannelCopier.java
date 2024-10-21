package ru.mirea.pr2.task2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileChannelCopier extends AbstractFileCopier {
    public FileChannelCopier() throws IOException {
        super();
    }

    @Override
    void copyFile() throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {

            FileChannel sourceChannel = fis.getChannel();
            FileChannel destinationChannel = fos.getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
            sourceChannel.close();
            destinationChannel.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
