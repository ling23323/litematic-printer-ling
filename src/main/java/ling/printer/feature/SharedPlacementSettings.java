package ling.printer.feature;

import java.util.List;

/** Shared placement policy, read live at the original decision points. */
public interface SharedPlacementSettings {
    boolean placeInAir();
    List<String> replaceableList();
}
