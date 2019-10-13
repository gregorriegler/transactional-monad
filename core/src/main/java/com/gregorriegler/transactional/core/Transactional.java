package com.gregorriegler.transactional.core;

public class Transactional {
    private final RunnableChain runnable;

    private Transactional(RunnableChain runnable) {
        this.runnable = runnable;
    }

    public static Transactional of(Runnable runnable) {
        return new Transactional(RunnableChain.of(runnable));
    }

    public void run(Transaction transaction) {
        try {
            transaction.begin();
            runnable.run();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }

    public Transactional andThen(Runnable runnable) {
        return new Transactional(this.runnable.andThen(runnable));
    }

    public Transactional andThen(Transactional anotherTransactional) {
        return new Transactional(this.runnable.andThen(anotherTransactional.runnable));
    }
}
