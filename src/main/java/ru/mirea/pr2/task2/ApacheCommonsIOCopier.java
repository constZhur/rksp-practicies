package ru.mirea.pr2.task2;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class ApacheCommonsIOCopier extends AbstractFileCopier {

    public ApacheCommonsIOCopier() throws IOException {
        super();
    }

    @Override
    void copyFile() throws IOException {
        File srcFile = new File(sourceFile);
        File destFile = new File(destinationFile);
        FileUtils.copyFile(srcFile, destFile);
    }
}
