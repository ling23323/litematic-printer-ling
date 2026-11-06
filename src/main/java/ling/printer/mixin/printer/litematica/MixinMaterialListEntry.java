package ling.printer.mixin.printer.litematica;

//#if MC >= 12111
import fi.dy.masa.litematica.materials.MaterialListEntry;
import ling.printer.integration.voidwarehouse.VoidWarehouseMaterialCache;
import ling.printer.integration.voidwarehouse.VoidWarehouseMaterialCounts;
import ling.printer.integration.voidwarehouse.VoidWarehouseMaterialIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Add warehouse stock to availability without changing unfinished world-block counts.
 * Litematica uses getCountMissing() for progress, filtering and export; collection gaps
 * belong in the GUI/HUD presentation, not in this shared getter.
 */
@Mixin(value = MaterialListEntry.class, remap = false)
public abstract class MixinMaterialListEntry {
    @Inject(method = "getCountAvailable", at = @At("RETURN"), cancellable = true, remap = false)
    private void litematicaPrinter$addVoidWarehouseStock(CallbackInfoReturnable<Integer> cir) {
        MaterialListEntry self = (MaterialListEntry) (Object) this;
        String id = VoidWarehouseMaterialIds.idOf(self.getStack());
        if (id == null) return;
        long warehouseAmount = VoidWarehouseMaterialCache.get().amountOf(id);
        if (warehouseAmount > 0L) {
            cir.setReturnValue(VoidWarehouseMaterialCounts.merge(cir.getReturnValueI(), warehouseAmount));
        }
    }
}
//#endif
