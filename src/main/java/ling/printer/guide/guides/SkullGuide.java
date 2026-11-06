package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import ling.printer.utils.minecraft.DirectionUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;

/**
 * 头颅
 */
public class SkullGuide extends Guide {

    public SkullGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        Direction facing = getProperty(requiredState, WallSkullBlock.FACING).orElse(null);

        if (requiredBlock instanceof SkullBlock) {
            int rotation = getProperty(requiredState, SkullBlock.ROTATION).orElseThrow();
            return Result.success(new Action()
                    .setSides(Direction.DOWN)
                    .setLookRotation(DirectionUtils.getOppositeRotation(rotation))
                    .setRequiresSupport());
        }
        if (requiredBlock instanceof WallSkullBlock && facing != null) {
            return Result.success(new Action()
                    .setSides(facing.getOpposite())
                    .setLookDirection(facing.getOpposite())
                    .setRequiresSupport());
        }
        return Result.SKIP;
    }
}
