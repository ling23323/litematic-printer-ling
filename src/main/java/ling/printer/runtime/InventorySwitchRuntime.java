package ling.printer.runtime;

import ling.printer.core.runtime.RuntimeComponent;
import ling.printer.core.runtime.RuntimeEvent;
import ling.printer.utils.InventorySwitchGuard;

/** Epoch boundary for the printer's local inventory-switch confirmation window. */
final class InventorySwitchRuntime implements RuntimeComponent {
    private final InventorySwitchGuard guard;

    InventorySwitchRuntime(InventorySwitchGuard guard) {
        this.guard = guard;
    }

    @Override
    public void onEpochChanged(RuntimeEvent.EpochChanged event) {
        this.guard.reset();
    }
}
