package com.gregorriegler.transactional.hibernate;

import com.gregorriegler.transactional.core.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class JpaTransaction implements Transaction {
    private final EntityTransaction entityTransaction;

    public static JpaTransaction with(EntityManager entityManager) {
        return new JpaTransaction(entityManager.getTransaction());
    }

    private JpaTransaction(EntityTransaction entityTransaction) {
        this.entityTransaction = entityTransaction;
    }

    @Override public void begin() {
        entityTransaction.begin();
    }

    @Override public void commit() {
        entityTransaction.commit();
    }

    @Override public void rollback() {
        entityTransaction.rollback();
    }
}
