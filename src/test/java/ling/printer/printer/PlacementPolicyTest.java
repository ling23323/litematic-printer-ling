package ling.printer.placement;

import ling.printer.feature.FeatureActionPolicies;
import ling.printer.config.Configs;
import ling.printer.placement.action.ActionPort.ActionSource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlacementPolicyTest {
    @Test void reserveIsEvaluatedAtExecutionAndAppliesOnlyToPrint() {
        boolean enabled = Configs.Print.PRINT_RESERVE_ITEMS.getBooleanValue();
        int count = Configs.Print.PRINT_RESERVE_ITEM_COUNT.getIntegerValue();
        try {
            var print = FeatureActionPolicies.forSource(ActionSource.PRINT);
            Configs.Print.PRINT_RESERVE_ITEMS.setBooleanValue(false);
            assertEquals(-1, print.reserveCount().getAsInt());
            Configs.Print.PRINT_RESERVE_ITEMS.setBooleanValue(true);
            Configs.Print.PRINT_RESERVE_ITEM_COUNT.setIntegerValue(32);
            assertEquals(32, print.reserveCount().getAsInt());
            for (var source : ActionSource.values()) {
                if (source != ActionSource.PRINT) {
                    assertEquals(-1, FeatureActionPolicies.forSource(source).reserveCount().getAsInt());
                    assertFalse(FeatureActionPolicies.forSource(source).easyPlaceProtocol());
                }
            }
            assertTrue(print.easyPlaceProtocol());
            assertTrue(FeatureActionPolicies.forSource(ActionSource.MINE).suppressAnvilScreen(),
                    "trench placement keeps the previous FILL screen policy");
            assertFalse(FeatureActionPolicies.forSource(ActionSource.FLUID).suppressAnvilScreen());
        } finally {
            Configs.Print.PRINT_RESERVE_ITEMS.setBooleanValue(enabled);
            Configs.Print.PRINT_RESERVE_ITEM_COUNT.setIntegerValue(count);
        }
    }
}
