package ling.printer.handler.handlers.print;

import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.world.level.block.state.BlockState;

public interface PrintTaskAction {
    /** State expected from this individual workflow action, not necessarily the schematic final state. */
    default BlockState expectedBlockState(SchematicBlockContext context, Action action) {
        return context.requiredState;
    }

    default void onQueued(SchematicBlockContext context, Action action) {
    }

    void onSuccess(SchematicBlockContext context, Action action);

    void onFailure(SchematicBlockContext context, Action action);

    default void onCancelled(SchematicBlockContext context, Action action) {
    }

    default boolean stopIterationAfterAction() {
        return true;
    }
}
