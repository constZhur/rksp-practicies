package ru.mirea.pr2.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FilesClassCopier extends AbstractFileCopier {
    public FilesClassCopier() throws IOException {
        super();
    }

    @Override
    void copyFile() throws IOException {
        Path sourcePath = Path.of(sourceFile);
        Path destinationPath = Path.of(destinationFile);
        Files.copy(sourcePath, destinationPath,
                StandardCopyOption.REPLACE_EXISTING);
    }
}
