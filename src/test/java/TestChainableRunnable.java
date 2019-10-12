import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TestChainableRunnable {
    @Test
    void
    should_run_runnable() {
        SomeInterface someInterface = mock(SomeInterface.class);

        ChainableRunnable chainableRunnable = ChainableRunnable.of(someInterface::method);

        chainableRunnable.run();

        verify(someInterface).method();
    }

}

class ChainableRunnable {
    private final Runnable runnable;

    private ChainableRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    static ChainableRunnable of(Runnable runnable) {
        return new ChainableRunnable(runnable);
    }

    void run() {
        runnable.run();
    }
}

interface SomeInterface {
    void method();
}