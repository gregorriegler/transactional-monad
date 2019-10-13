package com.gregorriegler.transactional.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunnableChain {
    private final List<Runnable> runnables;

    private RunnableChain(Runnable runnable) {
        this.runnables = Collections.singletonList(runnable);
    }

    private RunnableChain(List<Runnable> runnables) {
        this.runnables = Collections.unmodifiableList(runnables);
    }

    public static RunnableChain of(Runnable runnable) {
        return new RunnableChain(runnable);
    }

    public void run() {
        runnables.forEach(Runnable::run);
    }

    public RunnableChain andThen(Runnable runnable) {
        ArrayList<Runnable> list = new ArrayList<>(runnables);
        list.add(runnable);
        return new RunnableChain(list);
    }

    public RunnableChain andThen(RunnableChain chain) {
        ArrayList<Runnable> runnables = new ArrayList<>();
        runnables.addAll(this.runnables);
        runnables.addAll(chain.runnables);
        return new RunnableChain(runnables);
    }
}
