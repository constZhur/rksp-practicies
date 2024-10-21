package ru.mirea.pr3.task4;

public class FileProcessingSystem {

    public static void main(String[] args) {
        int queueCapacity = 5;

        FileQueue fileQueue = new FileQueue(queueCapacity);

        String[] supportedFileTypes = {"XML", "JSON", "XLS"};
        for (String fileType : supportedFileTypes) {
            new FileProcessor(fileType)
                    .processFiles(fileQueue.getFileObservable())
                    .subscribe(
                            () -> {},
                            throwable -> System.err.println("Error processing file: " + throwable));
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
