package ling.printer.handler.scan;

import ling.printer.enums.ScanState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScanLifecycleTest {
    @Test
    void resetReturnsToFullScanAndClearsIdleState() {
        ScanLifecycle lifecycle = new ScanLifecycle();
        lifecycle.setState(ScanState.PARTIAL);
        lifecycle.idlePolicy().recordLazyProbe(false, false);

        lifecycle.reset();

        assertEquals(ScanState.FULL, lifecycle.state());
    }
}
