package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.world.level.block.HopperBlock;

/**
 * 漏斗
 */
public class HopperGuide extends Guide {

    public HopperGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        var hopperFacing = getProperty(requiredState, HopperBlock.FACING).orElseThrow();
        return Result.success(new Action().setSides(hopperFacing));
    }
}
