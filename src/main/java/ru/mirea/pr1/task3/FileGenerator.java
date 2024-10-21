package ru.mirea.pr1.task3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class FileGenerator implements Runnable {
    private BlockingQueue<File> queue;

    public FileGenerator(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] fileTypes = {"XML", "JSON", "XLS"};

        while (true) {
            try {
                Thread.sleep(random.nextInt(901) + 100);
                String randomFileType =
                        fileTypes[random.nextInt(fileTypes.length)];
                int randomFileSize = random.nextInt(91) + 10;
                File file = new File(randomFileType, randomFileSize);
                queue.put(file);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
