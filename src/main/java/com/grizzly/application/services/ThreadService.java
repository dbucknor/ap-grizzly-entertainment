package com.grizzly.application.services;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadService {
    public static ExecutorService executor = Executors.newCachedThreadPool();

}
