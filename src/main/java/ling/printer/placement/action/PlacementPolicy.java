package ling.printer.placement.action;

import java.util.function.IntSupplier;

/** Execution constraints supplied by the producer; a negative reserve means unrestricted. */
public record PlacementPolicy(IntSupplier reserveCount, boolean suppressAnvilScreen, boolean easyPlaceProtocol) {
}
