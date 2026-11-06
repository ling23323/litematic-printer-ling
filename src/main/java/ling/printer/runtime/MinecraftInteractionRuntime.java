package ling.printer.runtime;

import ling.printer.core.runtime.RuntimeComponent;
import ling.printer.core.runtime.RuntimeEvent;
import ling.printer.mixin_extension.MultiPlayerGameModeExtension;
import ling.printer.utils.minecraft.NetworkUtils;
import net.minecraft.client.Minecraft;

/** Lifecycle owner for transient Minecraft interaction state exposed by thin mixin ports. */
final class MinecraftInteractionRuntime implements RuntimeComponent {
    private final Minecraft client;

    MinecraftInteractionRuntime(Minecraft client) {
        this.client = client;
    }

    @Override
    public void onEpochChanged(RuntimeEvent.EpochChanged event) {
        if (this.client.gameMode instanceof MultiPlayerGameModeExtension extension) {
            extension.ling$resetRuntime();
        }
        NetworkUtils.clearScopedLookOverride();
    }
}
