package com.eatcountdown;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class EatCountdownConfigScreen extends Screen {

    private final Screen parent;
    private TextFieldWidget xField, yField, colorField;

    public EatCountdownConfigScreen(Screen parent) {
        super(Text.literal("Eat Countdown – Ayarlar"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        EatCountdownConfig cfg = EatCountdownConfig.get();

        int centerX = this.width / 2;
        int startY = this.height / 2 - 60;

        // ── X konumu ────────────────────────────────────────────────
        xField = new TextFieldWidget(textRenderer, centerX - 50, startY, 100, 20,
                Text.literal("X"));
        xField.setMaxLength(5);
        xField.setText(String.valueOf(cfg.posX));
        addDrawableChild(xField);

        // ── Y konumu ────────────────────────────────────────────────
        yField = new TextFieldWidget(textRenderer, centerX - 50, startY + 30, 100, 20,
                Text.literal("Y"));
        yField.setMaxLength(5);
        yField.setText(String.valueOf(cfg.posY));
        addDrawableChild(yField);

        // ── Renk (AARRGGBB hex) ─────────────────────────────────────
        colorField = new TextFieldWidget(textRenderer, centerX - 50, startY + 60, 100, 20,
                Text.literal("Renk"));
        colorField.setMaxLength(10);
        colorField.setText(String.format("0x%08X", cfg.color));
        addDrawableChild(colorField);

        // ── Kaydet ──────────────────────────────────────────────────
        addDrawableChild(ButtonWidget.builder(Text.literal("Kaydet"), btn -> {
            try { cfg.posX = Integer.parseInt(xField.getText().trim()); } catch (NumberFormatException ignored) {}
            try { cfg.posY = Integer.parseInt(yField.getText().trim()); } catch (NumberFormatException ignored) {}
            try {
                String hex = colorField.getText().trim().replace("0x", "").replace("0X", "");
                cfg.color = (int) Long.parseLong(hex, 16);
            } catch (NumberFormatException ignored) {}
            EatCountdownConfig.save();
            MinecraftClient.getInstance().setScreen(parent);
        }).dimensions(centerX - 50, startY + 100, 100, 20).build());

        // ── Geri ────────────────────────────────────────────────────
        addDrawableChild(ButtonWidget.builder(Text.literal("İptal"), btn ->
                MinecraftClient.getInstance().setScreen(parent)
        ).dimensions(centerX - 50, startY + 125, 100, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        int cx = this.width / 2;
        int sy = this.height / 2 - 60;
        ctx.drawCenteredTextWithShadow(textRenderer, this.title, cx, sy - 20, 0xFFFFFF);
        ctx.drawTextWithShadow(textRenderer, "HUD X:", cx - 80, sy + 5, 0xAAAAAA);
        ctx.drawTextWithShadow(textRenderer, "HUD Y:", cx - 80, sy + 35, 0xAAAAAA);
        ctx.drawTextWithShadow(textRenderer, "Renk (AARRGGBB):", cx - 80, sy + 65, 0xAAAAAA);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }
}
