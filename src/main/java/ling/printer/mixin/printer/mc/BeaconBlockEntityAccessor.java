package ling.printer.mixin.printer.mc;

//#if MC > 12004
import net.minecraft.core.Holder;
//#endif
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

//#if MC > 12004
import java.util.List;
//#endif

@Mixin(BeaconBlockEntity.class)
public interface BeaconBlockEntityAccessor {
    @Accessor("BEACON_EFFECTS")
    //#if MC > 12004
    static List<List<Holder<MobEffect>>> ling$getBeaconEffects() {
    //#else
    //$$ static MobEffect[][] ling$getBeaconEffects() {
    //#endif
        throw new AssertionError();
    }

    @Mutable
    @Accessor("BEACON_EFFECTS")
    static void ling$setBeaconEffects(
            //#if MC > 12004
            List<List<Holder<MobEffect>>> beaconEffects
            //#else
            //$$ MobEffect[][] beaconEffects
            //#endif
    ) {
        throw new AssertionError();
    }
}
