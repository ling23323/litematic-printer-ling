package ling.printer.mixin.printer.litematica;

import com.llamalad7.mixinextras.sugar.Local;
import fi.dy.masa.litematica.render.schematic.ChunkCacheSchematic;
import fi.dy.masa.litematica.render.schematic.ChunkRendererSchematicVbo;
import ling.printer.config.Configs;
import ling.printer.mixin_extension.InventoryAvailabilityRenderExtension;
import ling.printer.runtime.RuntimeAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Filters schematic blocks during mesh construction without binding to a version-specific signature. */
@Mixin(value = ChunkRendererSchematicVbo.class, remap = false)
public abstract class MixinChunkRendererSchematicVbo implements InventoryAvailabilityRenderExtension {
    @Shadow
    protected ChunkCacheSchematic schematicWorldView;

    @Shadow
    protected abstract void setNeedsUpdate(boolean immediate);

    @Unique
    private long litematicaPrinter$lastAvailabilityRevision = Long.MIN_VALUE;

    @Inject(method = "renderBlocksAndOverlay", at = @At("HEAD"), cancellable = true)
    private void litematicaPrinter$filterUnavailableBlock(CallbackInfo ci,
            @Local(argsOnly = true) BlockPos pos) {
        if (!Configs.Core.RENDER_ONLY_HOLDING_ITEMS.getBooleanValue()) {
            return;
        }

        BlockState stateSchematic = this.schematicWorldView.getBlockState(pos);
        Item item = stateSchematic.getBlock().asItem();
        if (!RuntimeAccess.get().inventoryAvailability().isAvailable(item)) {
            ci.cancel();
        }
    }

    @Override
    public void ling$refreshInventoryAvailability() {
        long revision = RuntimeAccess.get().inventoryAvailability().availabilityRevision();
        if (revision != this.litematicaPrinter$lastAvailabilityRevision) {
            this.litematicaPrinter$lastAvailabilityRevision = revision;
            this.setNeedsUpdate(true);
        }
    }
}
