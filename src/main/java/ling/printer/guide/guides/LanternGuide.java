package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.LanternBlock;

/**
 * 灯笼
 */
public class LanternGuide extends Guide {

    public LanternGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        if (getProperty(requiredState, LanternBlock.HANGING).orElse(false)) {
            return Result.success(new Action()
                    .setSides(Direction.UP)
                    .setLookDirection(Direction.UP)
                    .setRequiresSupport());
        }
        return Result.success(new Action()
                .setSides(Direction.DOWN)
                .setLookDirection(Direction.DOWN)
                .setRequiresSupport());
    }
}
