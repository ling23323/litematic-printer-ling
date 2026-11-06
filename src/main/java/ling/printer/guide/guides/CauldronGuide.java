package ling.printer.guide.guides;

import ling.printer.config.Configs;
import ling.printer.enums.BlockMatchResult;
import ling.printer.guide.Guide;
import ling.printer.guide.Result;
import ling.printer.placement.SchematicBlockContext;
import ling.printer.placement.action.ClickAction;
import ling.printer.utils.InteractionUtils;
import ling.printer.utils.InventoryUtils;
import ling.printer.utils.PotionStackUtils;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

/**
 * 炼药锅
 */
public class CauldronGuide extends Guide {

    public CauldronGuide(SchematicBlockContext context) {
        super(context);
    }

    @Override
    protected Result onBuildActionWrongState(BlockMatchResult state) {
        if (!requiredState.is(Blocks.WATER_CAULDRON)) {
            return Result.SKIP;
        }
        Optional<Integer> currentLevel = getProperty(currentState, LayeredCauldronBlock.LEVEL);
        Optional<Integer> requiredLevel = getProperty(requiredState, LayeredCauldronBlock.LEVEL);

        if (currentLevel.isEmpty() || requiredLevel.isEmpty()) {
            return Result.SKIP;
        }

        if (currentLevel.get() > requiredLevel.get()) {
            if (InventoryUtils.playerHasAccessToItem(client.player, Items.GLASS_BOTTLE)) {
                return Result.success(new ClickAction().setItem(Items.GLASS_BOTTLE));
            }
        }
        if (currentLevel.get() < requiredLevel.get()) {
            return this.buildWaterFillAction(requiredLevel.get());
        }
        return Result.SKIP;
    }

    @Override
    protected Result onBuildActionWrongBlock(BlockMatchResult state) {
        if (requiredState.is(Blocks.WATER_CAULDRON) && currentState.is(Blocks.CAULDRON)) {
            int requiredLevel = getProperty(requiredState, LayeredCauldronBlock.LEVEL).orElse(1);
            return this.buildWaterFillAction(requiredLevel);
        }
        if (requiredState.is(Blocks.CAULDRON)
                && currentState.is(Blocks.WATER_CAULDRON)
                && InventoryUtils.playerHasAccessToItem(client.player, Items.GLASS_BOTTLE)) {
            return Result.success(new ClickAction().setItem(Items.GLASS_BOTTLE));
        }
        if (Configs.Print.BREAK_WRONG_BLOCK.getBooleanValue()
                && InteractionUtils.canBreakBlock(blockPos)) {
                InteractionUtils.getRuntime().add(context);
        }
        return Result.SKIP;
    }

    private Result buildWaterFillAction(int requiredLevel) {
        if (requiredLevel == LayeredCauldronBlock.MAX_FILL_LEVEL
                && InventoryUtils.playerHasItemInInventory(client.player, Items.WATER_BUCKET)) {
            return Result.success(new ClickAction().setItem(Items.WATER_BUCKET));
        }
        ItemStack waterPotion = PotionStackUtils.createWaterPotionStack();
        if (InventoryUtils.playerHasAccessToMatchingStack(
                client.player,
                waterPotion,
                PotionStackUtils::isWaterPotion
        )) {
            return Result.success(new ClickAction()
                    .setItem(Items.POTION)
                    .setRequiredStackPredicate(PotionStackUtils::isWaterPotion)
                    .setRequiredCreativeStack(waterPotion));
        }
        if (requiredLevel == LayeredCauldronBlock.MAX_FILL_LEVEL) {
            InventoryUtils.playerHasAccessToItem(client.player, Items.WATER_BUCKET);
        }
        return Result.SKIP;
    }
}
