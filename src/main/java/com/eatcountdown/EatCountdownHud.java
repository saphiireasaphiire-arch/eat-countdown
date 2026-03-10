package com.eatcountdown;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;

public class EatCountdownHud implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.world == null) return;
        if (client.currentScreen != null) return;
        if (!client.player.isUsingItem()) return;

        ItemStack using = client.player.getActiveItem();
        if (using.isEmpty()) return;

        int ticksLeft = client.player.getItemUseTimeLeft();
        float secondsLeft = ticksLeft / 20.0f;

        EatCountdownConfig cfg = EatCountdownConfig.get();
        String text = String.format("%.1f", secondsLeft);

        ctx.drawTextWithShadow(
                client.textRenderer,
                text,
                cfg.posX,
                cfg.posY,
                cfg.color
        );
    }
}
