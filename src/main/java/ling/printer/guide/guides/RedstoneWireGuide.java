package ling.printer.guide.guides;

import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RedstoneSide;

/**
 * 红石线
 */
public class RedstoneWireGuide extends Guide {
    public RedstoneWireGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        RedstoneSide rNorth = getProperty(requiredState, BlockStateProperties.NORTH_REDSTONE).orElse(RedstoneSide.NONE);
        RedstoneSide rEast = getProperty(requiredState, BlockStateProperties.EAST_REDSTONE).orElse(RedstoneSide.NONE);
        RedstoneSide rSouth = getProperty(requiredState, BlockStateProperties.SOUTH_REDSTONE).orElse(RedstoneSide.NONE);
        RedstoneSide rWest = getProperty(requiredState, BlockStateProperties.WEST_REDSTONE).orElse(RedstoneSide.NONE);

        RedstoneSide cNorth = getProperty(currentState, BlockStateProperties.NORTH_REDSTONE).orElse(RedstoneSide.NONE);
        RedstoneSide cEast = getProperty(currentState, BlockStateProperties.EAST_REDSTONE).orElse(RedstoneSide.NONE);
        RedstoneSide cSouth = getProperty(currentState, BlockStateProperties.SOUTH_REDSTONE).orElse(RedstoneSide.NONE);
        RedstoneSide cWest = getProperty(currentState, BlockStateProperties.WEST_REDSTONE).orElse(RedstoneSide.NONE);

        boolean allNoneRequired = rNorth == RedstoneSide.NONE
                && rSouth == RedstoneSide.NONE
                && rEast == RedstoneSide.NONE
                && rWest == RedstoneSide.NONE;

        boolean allSideCurrent = cNorth == RedstoneSide.SIDE
                && cSouth == RedstoneSide.SIDE
                && cEast == RedstoneSide.SIDE
                && cWest == RedstoneSide.SIDE;

        if (allNoneRequired && allSideCurrent) {
            return Result.success(new ClickAction().setItem(Items.AIR));
        }
        return Result.SKIP;
    }
}
