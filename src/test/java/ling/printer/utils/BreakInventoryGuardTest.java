package ling.printer.utils;

import ling.printer.core.action.ResourceLease;
import ling.printer.core.action.ActionCoordinator;
import ling.printer.core.action.ActionRequest;
import ling.printer.core.runtime.RuntimeEpoch;
import org.junit.jupiter.api.Test;
import java.util.EnumSet;
import static org.junit.jupiter.api.Assertions.*;

class BreakInventoryGuardTest {
    @Test void queuedBreakingWaitsForEachInventoryResourceAndResumesAfterRelease() {
        var broker = new ActionCoordinator();
        for (var resource : new ResourceLease[]{ResourceLease.MAIN_HAND, ResourceLease.INVENTORY, ResourceLease.CONTAINER}) {
            assertTrue(broker.tryAdmit(new ActionRequest("quick_shulker", new RuntimeEpoch(1), EnumSet.of(resource), 0), 0).isPresent());
            assertFalse(InteractionUtils.canAdvanceBreakQueue(broker::isHeld, false, false));
            broker.releaseOwner("quick_shulker");
            assertTrue(InteractionUtils.canAdvanceBreakQueue(broker::isHeld, false, false));
        }
        assertFalse(InteractionUtils.canAdvanceBreakQueue(broker::isHeld, true, false));
        assertFalse(InteractionUtils.canAdvanceBreakQueue(broker::isHeld, false, true));
    }
}
