package com.eatcountdown;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class EatCountdownConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("eat-countdown.json");

    // ── Varsayılan değerler ──────────────────────────────────────────
    public int posX = 10;
    public int posY = 10;
    /** ARGB hex renk, örn: 0xFFFFFF00 = sarı */
    public int color = 0xFFFFFFFF;
    /** Yalnızca yiyecek/içecek için mi göstersin? */
    public boolean foodOnly = true;

    // ── Singleton ────────────────────────────────────────────────────
    private static EatCountdownConfig instance;

    public static EatCountdownConfig get() {
        if (instance == null) load();
        return instance;
    }

    // ── Yükle / Kaydet ───────────────────────────────────────────────
    public static void load() {
        File file = CONFIG_PATH.toFile();
        if (file.exists()) {
            try (Reader r = new FileReader(file)) {
                instance = GSON.fromJson(r, EatCountdownConfig.class);
                if (instance == null) instance = new EatCountdownConfig();
            } catch (IOException e) {
                instance = new EatCountdownConfig();
            }
        } else {
            instance = new EatCountdownConfig();
            save();
        }
    }

    public static void save() {
        try (Writer w = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(instance, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
