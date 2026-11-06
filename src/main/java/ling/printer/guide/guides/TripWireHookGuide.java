package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.world.level.block.TripWireHookBlock;

/**
 * 绊线钩
 */
public class TripWireHookGuide extends Guide {

    public TripWireHookGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        var facing = getProperty(requiredState, TripWireHookBlock.FACING).orElseThrow();
        return Result.success(new Action()
                .setSides(facing.getOpposite())
                .setRequiresSupport()
        );
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        return Result.SKIP;
    }
}
