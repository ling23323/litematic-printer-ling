package ling.printer.utils.minecraft;

import ling.printer.config.Configs;
import ling.printer.mixin.printer.mc.BeaconBlockEntityAccessor;
//#if MC > 12004
import net.minecraft.core.Holder;
//#endif
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

//#if MC > 12004
import java.util.List;
//#endif

public final class BeaconEffectSync {
    private BeaconEffectSync() {
    }

    @SuppressWarnings("FieldCanBeLocal")
    private static boolean originalCaptured;

    //#if MC > 12004
    private static List<List<Holder<MobEffect>>> originalBeaconEffects;
    private static final List<List<Holder<MobEffect>>> UNLOCKED_BEACON_EFFECTS = List.of(
            List.of(MobEffects.SPEED, MobEffects.HASTE),
            List.of(MobEffects.REGENERATION, MobEffects.RESISTANCE),
            List.of(MobEffects.JUMP_BOOST, MobEffects.STRENGTH),
            List.of()
    );
    //#else
    //$$ private static MobEffect[][] originalBeaconEffects;
    //$$ private static final MobEffect[][] UNLOCKED_BEACON_EFFECTS = new MobEffect[][]{
    //$$         {MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED},
    //$$         {MobEffects.REGENERATION, MobEffects.DAMAGE_RESISTANCE},
    //$$         {MobEffects.JUMP, MobEffects.DAMAGE_BOOST},
    //$$         {}
    //$$ };
    //#endif

    public static void syncFromConfig() {
        if (!originalCaptured) {
            originalBeaconEffects = BeaconBlockEntityAccessor.ling$getBeaconEffects();
            originalCaptured = true;
        }

        if (Configs.Special.UNLOCK_BEACON_EFFECTS.getBooleanValue()) {
            if (BeaconBlockEntityAccessor.ling$getBeaconEffects() != UNLOCKED_BEACON_EFFECTS) {
                BeaconBlockEntityAccessor.ling$setBeaconEffects(UNLOCKED_BEACON_EFFECTS);
            }
            return;
        }

        if (BeaconBlockEntityAccessor.ling$getBeaconEffects() != originalBeaconEffects) {
            BeaconBlockEntityAccessor.ling$setBeaconEffects(originalBeaconEffects);
        }
    }
}
