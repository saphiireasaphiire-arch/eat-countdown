package com.eatcountdown;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class EatCountdownHud implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext ctx, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        // Oyuncu ve dünya yoksa çizme
        if (client.player == null || client.world == null) return;
        // Oyun ekranı kapalıysa çizme (envanter açıkken vs.)
        if (client.currentScreen != null) return;

        if (!client.player.isUsingItem()) return;

        ItemStack using = client.player.getActiveItem();
        if (using.isEmpty()) return;

        EatCountdownConfig cfg = EatCountdownConfig.get();

        // foodOnly açıksa sadece yiyecek/içecek göster
        if (cfg.foodOnly) {
            UseAction action = using.getUseAction();
            if (action != UseAction.EAT && action != UseAction.DRINK) return;
        }

        // Kalan tick → saniye
        int ticksLeft = client.player.getItemUseTimeLeft();
        float secondsLeft = ticksLeft / 20.0f;

        // "2.4" gibi tek ondalık
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
