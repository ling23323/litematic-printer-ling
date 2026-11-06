package ling.printer.handler.scan;

/** Describes why a scan candidate source currently has no next item. */
public enum ScanAvailability {
    READY,
    PAUSED,
    COMPLETE,
    CANCELLED,
    FAILED
}
