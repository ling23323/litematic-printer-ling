package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.world.item.Items;

/**
 * 攀爬植物
 */
public class ClimbingPlantGuide extends Guide {

    public ClimbingPlantGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        if (requiredBlock instanceof net.minecraft.world.level.block.BigDripleafStemBlock) {
            return Result.success(new Action().setItem(Items.BIG_DRIPLEAF));
        }
        if (requiredBlock instanceof net.minecraft.world.level.block.CaveVinesBlock
                || requiredBlock instanceof net.minecraft.world.level.block.CaveVinesPlantBlock) {
            return Result.success(new Action().setItem(Items.GLOW_BERRIES).setRequiresSupport());
        }
        if (requiredBlock instanceof net.minecraft.world.level.block.WeepingVinesBlock
                || requiredBlock instanceof net.minecraft.world.level.block.WeepingVinesPlantBlock) {
            return Result.success(new Action().setItem(Items.WEEPING_VINES).setRequiresSupport());
        }
        if (requiredBlock instanceof net.minecraft.world.level.block.TwistingVinesBlock
                || requiredBlock instanceof net.minecraft.world.level.block.TwistingVinesPlantBlock) {
            return Result.success(new Action().setItem(Items.TWISTING_VINES).setRequiresSupport());
        }
        return Result.SKIP;
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        return Result.SKIP;
    }
}
