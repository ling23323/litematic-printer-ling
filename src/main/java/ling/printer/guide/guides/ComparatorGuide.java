package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.level.block.ComparatorBlock;

/**
 * 红石比较器
 */
public class ComparatorGuide extends Guide {

    public ComparatorGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        if (!getProperty(requiredState, ComparatorBlock.MODE).equals(getProperty(currentState, ComparatorBlock.MODE))) {
            return Result.success(new ClickAction());
        }
        return Result.SKIP;
    }
}
