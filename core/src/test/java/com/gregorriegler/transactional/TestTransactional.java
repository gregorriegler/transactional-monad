package com.gregorriegler.transactional;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class TestTransactional {

    private final Transaction transaction = mock(Transaction.class);
    private final SomeRepository repository = mock(SomeRepository.class);
    private final InOrder inOrder = Mockito.inOrder(transaction, repository);

    @Test void
    should_execute_function_within_a_transaction() {
        Transactional.of(repository::update).run(transaction);

        inOrder.verify(transaction).begin();
        inOrder.verify(repository).update();
        inOrder.verify(transaction).commit();
    }

    @Test void
    should_rollback_on_exception() {
        RuntimeException expectedException = new RuntimeException();
        doThrow(expectedException).when(repository).update();

        assertThatThrownBy(() ->
            Transactional.of(repository::update).run(transaction)
        ).isSameAs(expectedException);

        inOrder.verify(transaction).begin();
        inOrder.verify(repository).update();
        inOrder.verify(transaction).rollback();
    }

    @Test void
    should_chain_functions_within_transaction() {
        Transactional.of(repository::update)
            .andThen(repository::updateToo)
            .run(transaction);

        inOrder.verify(transaction).begin();
        inOrder.verify(repository).update();
        inOrder.verify(repository).updateToo();
        inOrder.verify(transaction).commit();
    }

    @Test void
    should_flatten_if_necessary() {
        Transactional transactional = Transactional.of(repository::update);
        Transactional anotherTransactional = Transactional.of(repository::updateToo);

        transactional.andThen(anotherTransactional).run(transaction);

        inOrder.verify(transaction).begin();
        inOrder.verify(repository).update();
        inOrder.verify(repository).updateToo();
        inOrder.verify(transaction).commit();
    }

    interface SomeRepository {
        void update();

        void updateToo();
    }

}
