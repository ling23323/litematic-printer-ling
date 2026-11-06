package ling.printer.handler.handlers.print;

import ling.printer.placement.SchematicBlockContext;
import ling.printer.interaction.StrippableBlockPort;
import org.jetbrains.annotations.Nullable;

import java.util.function.LongSupplier;

public final class PrintTasks {
    private PrintTasks() {
    }

    @Nullable
    public static PrintTask tryCreate(
            SchematicBlockContext context,
            LongSupplier tickClock,
            StrippableBlockPort strippableBlocks
    ) {
        PrintTask water = WaterPrintTask.tryCreate(context, tickClock);
        return water != null ? water : StripLogPrintTask.tryCreate(context, tickClock, strippableBlocks);
    }
}
