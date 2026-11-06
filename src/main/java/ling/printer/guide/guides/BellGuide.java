package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.state.properties.BellAttachType;

/**
 * 钟
 */
public class BellGuide extends Guide {
    public BellGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        Direction facing = getProperty(requiredState, BellBlock.FACING).orElseThrow();
        BellAttachType bellAttachment = getProperty(requiredState, BellBlock.ATTACHMENT).orElseThrow();

        Direction side = switch (bellAttachment) {
            case FLOOR -> Direction.DOWN;
            case CEILING -> Direction.UP;
            default -> facing;
        };

        Direction look = (bellAttachment == BellAttachType.SINGLE_WALL || bellAttachment == BellAttachType.DOUBLE_WALL)
                ? null : facing;

        return Result.success(new Action().setSides(side).setLookDirection(look));
    }
}
