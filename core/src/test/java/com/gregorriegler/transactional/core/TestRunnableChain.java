package com.gregorriegler.transactional.core;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TestRunnableChain {

    private final SomeInterface someInterface = mock(SomeInterface.class);

    @Test
    void
    should_run_runnable() {
        RunnableChain chain = RunnableChain.of(someInterface::method);

        chain.run();

        verify(someInterface).method();
    }

    @Test
    void
    should_chain_runnables() {
        RunnableChain chain = RunnableChain.of(someInterface::method)
            .andThen(someInterface::anotherMethod);

        chain.run();

        InOrder inOrder = Mockito.inOrder(someInterface);
        inOrder.verify(someInterface).method();
        inOrder.verify(someInterface).anotherMethod();
    }

    @Test
    void
    should_flatten_out_runnables() {
        RunnableChain chain = RunnableChain.of(someInterface::method)
            .andThen(RunnableChain.of(someInterface::anotherMethod));

        chain.run();

        InOrder inOrder = Mockito.inOrder(someInterface);
        inOrder.verify(someInterface).method();
        inOrder.verify(someInterface).anotherMethod();
    }

    interface SomeInterface {
        void method();

        void anotherMethod();
    }
}
