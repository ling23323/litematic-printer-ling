package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AnvilBlock;

/**
 * 铁砧。
 */
public class AnvilGuide extends Guide {

    public AnvilGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        Direction anvilFacing = getProperty(requiredState, AnvilBlock.FACING).orElseThrow();
        return Result.success(new Action().setLookDirection(anvilFacing.getCounterClockWise()));
    }
}
