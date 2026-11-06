package ling.printer.handler.handlers.bedrock;

import ling.printer.utils.minecraft.MessageUtils;
import ling.printer.utils.minecraft.StringUtils;

public final class BedrockMessages {
    private BedrockMessages() {
    }

    public static void actionBar(String key) {
        MessageUtils.setOverlayMessage(StringUtils.translatable(key), false);
    }
}
