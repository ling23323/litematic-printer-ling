package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.level.block.RepeaterBlock;

/**
 * 红石中继器
 */
public class RepeaterGuide extends Guide {

    public RepeaterGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        if (!getProperty(requiredState, RepeaterBlock.DELAY).equals(getProperty(currentState, RepeaterBlock.DELAY))) {
            return Result.success(new ClickAction());
        }
        return Result.SKIP;
    }
}
