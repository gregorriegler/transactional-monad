package com.gregorriegler.transactional.core;

public interface Transaction {
    void begin();

    void commit();

    void rollback();
}
