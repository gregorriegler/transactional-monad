package com.gregorriegler.transactional;

public interface Transaction {
    void begin();

    void commit();

    void rollback();
}
