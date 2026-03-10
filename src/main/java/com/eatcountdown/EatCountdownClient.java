package com.eatcountdown;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class EatCountdownClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EatCountdownConfig.load();
        HudRenderCallback.EVENT.register(new EatCountdownHud());
    }
}
