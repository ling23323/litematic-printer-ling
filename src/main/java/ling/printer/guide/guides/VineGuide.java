package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.Action;
import ling.printer.placement.PrinterUtils;
import net.minecraft.core.Direction;

/**
 * 藤蔓/发光地衣
 */
public class VineGuide extends Guide {

    public VineGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionMissingBlock(BlockMatchResult state) {
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN && requiredBlock instanceof net.minecraft.world.level.block.VineBlock) continue;
            Object value = PrinterUtils.getPropertyByName(requiredState, direction.name());
            if (value instanceof Boolean && (Boolean) value) {
                return Result.success(new Action().setSides(direction));
            }
        }
        return Result.SKIP;
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN && requiredBlock instanceof net.minecraft.world.level.block.VineBlock) continue;
            Object value = PrinterUtils.getPropertyByName(requiredState, direction.name());
            if (value instanceof Boolean && (Boolean) value) {
                return Result.success(new Action().setSides(direction).setLookDirection(direction));
            }
        }
        return Result.SKIP;
    }
}
