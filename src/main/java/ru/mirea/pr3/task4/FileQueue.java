package ru.mirea.pr3.task4;

import io.reactivex.Observable;
import ru.mirea.pr1.task3.File;

public class FileQueue {
    private final int capacity;
    private final Observable<File> fileObservable;

    public FileQueue(int capacity) {
        this.capacity = capacity;
        this.fileObservable = new FileGenerator().generateFile()
                .replay(capacity)
                .autoConnect();
    }

    public Observable<File> getFileObservable() {
        return fileObservable;
    }
}
