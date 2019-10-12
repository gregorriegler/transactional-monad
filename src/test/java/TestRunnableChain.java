import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

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
        RunnableChain chain = RunnableChain.of(someInterface::method).andThen(someInterface::anotherMethod);
        chain.run();

        InOrder inOrder = Mockito.inOrder(someInterface);
        inOrder.verify(someInterface).method();
        inOrder.verify(someInterface).anotherMethod();
    }

}

class RunnableChain {
    private final List<Runnable> runnables = new ArrayList<>();

    private RunnableChain(Runnable runnable) {
        this.runnables.add(runnable);
    }

    private RunnableChain(List<Runnable> runnables, Runnable runnable) {
        this.runnables.addAll(runnables);
        this.runnables.add(runnable);
    }

    static RunnableChain of(Runnable runnable) {
        return new RunnableChain(runnable);
    }

    void run() {
        runnables.forEach(Runnable::run);
    }

    RunnableChain andThen(Runnable runnable) {
        return new RunnableChain(runnables, runnable);
    }
}

interface SomeInterface {
    void method();

    void anotherMethod();
}