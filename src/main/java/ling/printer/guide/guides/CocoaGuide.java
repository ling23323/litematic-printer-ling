package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.world.level.block.CocoaBlock;

/**
 * 可可豆
 */
public class CocoaGuide extends Guide {

    public CocoaGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        var cocoaFacing = getProperty(requiredState, CocoaBlock.FACING).orElseThrow();
        return Result.success(new Action().setSides(cocoaFacing));
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        // AGE 由生长决定，环境决定 → 跳过
        return Result.SKIP;
    }
}
