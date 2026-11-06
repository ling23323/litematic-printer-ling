package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.level.block.LeverBlock;

/**
 * 拉杆
 */
public class LeverGuide extends Guide {

    public LeverGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        if (!getProperty(requiredState, LeverBlock.POWERED).equals(getProperty(currentState, LeverBlock.POWERED))) {
            return Result.success(new ClickAction());
        }
        return Result.SKIP;
    }
}
