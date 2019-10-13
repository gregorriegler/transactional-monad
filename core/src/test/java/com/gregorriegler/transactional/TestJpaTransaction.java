package com.gregorriegler.transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class TestJpaTransaction {

    private static final EntityManager entityManager = mock(EntityManager.class);
    private static final EntityTransaction entityTransaction = mock(EntityTransaction.class);
    private static JpaTransaction jpaTransaction;

    @BeforeAll static void setUp() {
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        jpaTransaction = JpaTransaction.with(entityManager);
    }

    @Test void
    should_delegate_begin() {
        jpaTransaction.begin();

        verify(entityTransaction).begin();
        verifyNoMoreInteractions(entityTransaction);
    }

    @Test void
    should_delegate_commit() {
        jpaTransaction.commit();

        verify(entityTransaction).commit();
        verifyNoMoreInteractions(entityTransaction);
    }

    @Test void
    should_delegate_rollback() {
        jpaTransaction.rollback();

        verify(entityTransaction).rollback();
        verifyNoMoreInteractions(entityTransaction);
    }
}
