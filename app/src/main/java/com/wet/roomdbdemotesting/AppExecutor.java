package com.wet.roomdbdemotesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Object lock = new Object();

    private static AppExecutor instance;
    private final Executor diskIO;

    public AppExecutor(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public static AppExecutor getInstance() {
        if (instance==null)
        {
            synchronized (lock)
            {
                instance = new AppExecutor(Executors.newSingleThreadExecutor());
            }

        }
        return instance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
