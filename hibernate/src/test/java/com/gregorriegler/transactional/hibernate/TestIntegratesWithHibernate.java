package com.gregorriegler.transactional.hibernate;

import com.gregorriegler.transactional.core.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

class TestIntegratesWithHibernate {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("com.gregorriegler.transactional.hibernate");
    }

    @BeforeEach
    void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test void
    should_integrate_with_hibernate() {
        String expectedId = "1234";

        Transactional.of(() -> {
            entityManager.persist(new Book(expectedId));
            entityManager.flush();
        }).run(JpaTransaction.with(entityManager));

        entityManager.clear();
        assertThat(entityManager.find(Book.class, expectedId).isbn).isEqualTo(expectedId);
    }
}

