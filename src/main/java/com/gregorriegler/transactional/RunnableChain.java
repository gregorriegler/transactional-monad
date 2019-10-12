package com.gregorriegler.transactional;

import java.util.ArrayList;
import java.util.List;

public class RunnableChain {
    private final List<Runnable> runnables = new ArrayList<>();

    private RunnableChain(Runnable runnable) {
        this.runnables.add(runnable);
    }

    private RunnableChain(List<Runnable> runnables, Runnable runnable) {
        this.runnables.addAll(runnables);
        this.runnables.add(runnable);
    }

    public static RunnableChain of(Runnable runnable) {
        return new RunnableChain(runnable);
    }

    public void run() {
        runnables.forEach(Runnable::run);
    }

    public RunnableChain andThen(Runnable runnable) {
        return new RunnableChain(runnables, runnable);
    }
}
