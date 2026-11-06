package ling.printer.utils.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MessageUtils {
    /** 节流表：key -> 上次真正显示的时间戳（毫秒）。 */
    private static final Map<String, Long> LAST_OVERLAY_MILLIS = new HashMap<>();

    public static void setOverlayMessage(Component message, boolean bl) {
        //#if MC > 260100
        //$$ Minecraft.getInstance().gui.hud.setOverlayMessage(message, bl);
        //#else
        Minecraft.getInstance().gui.setOverlayMessage(message, bl);
        //#endif
    }

    public static void setOverlayMessage(Component message) {
        setOverlayMessage(message, false);
    }

    public static void setOverlayMessage(String message) {
        setOverlayMessage(StringUtils.literal(message));
    }

    /**
     * 同一条提示在 {@code cooldownMillis} 内只显示一次。
     *
     * <p>逐方块的失败提示（缺支撑、跳过预留物品…）每 tick 都会触发一次，
     * 不节流的话 action bar 会一直闪，真正重要的信息反而被盖掉。</p>
     */
    public static void setOverlayMessageThrottled(String key, Component message, long cooldownMillis) {
        if (cooldownElapsed(key, cooldownMillis)) {
            setOverlayMessage(message);
        }
    }

    /**
     * 同上，但文本在<b>通过节流之后</b>才构建。
     * 逐方块失败提示每 tick 都会走一遍调用点，用这个重载可以省掉翻译查找和字符串拼接。
     */
    public static void setOverlayMessageThrottled(String key, long cooldownMillis, Supplier<Component> message) {
        if (cooldownElapsed(key, cooldownMillis)) {
            setOverlayMessage(message.get());
        }
    }

    private static boolean cooldownElapsed(String key, long cooldownMillis) {
        long now = System.currentTimeMillis();
        Long last = LAST_OVERLAY_MILLIS.get(key);
        if (last != null && now - last < cooldownMillis) {
            return false;
        }
        LAST_OVERLAY_MILLIS.put(key, now);
        return true;
    }

    public static void addMessage(Component message) {
        //#if MC > 260100
        //$$ Minecraft.getInstance().gui.hud.getChat().addClientSystemMessage(message);
        //#elseif MC>=260000
        Minecraft.getInstance().gui.getChat().addClientSystemMessage(message);
        //#else
        //$$ Minecraft.getInstance().gui.getChat().addMessage(message);
        //#endif
    }

    public static void addMessage(String message) {
        addMessage(StringUtils.literal(message));
    }
}
