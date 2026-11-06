package ling.printer.core.action;

import ling.printer.core.runtime.RuntimeEpoch;

public record ActionTicket(long id, ActionRequest request) {
    public boolean canSend(RuntimeEpoch epoch, long nowNanos) {
        return this.request.epoch().equals(epoch)
                && (this.request.deadlineNanos() <= 0L || nowNanos < this.request.deadlineNanos());
    }
}
