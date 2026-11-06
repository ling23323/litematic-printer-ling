package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.world.level.block.LadderBlock;

/**
 * 梯子
 */
public class LadderGuide extends Guide {

    public LadderGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        var ladderFacing = getProperty(requiredState, LadderBlock.FACING).orElseThrow();
        return Result.success(new Action()
                .setSides(ladderFacing)
                .setLookDirection(ladderFacing.getOpposite()));
    }
}
