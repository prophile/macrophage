package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public final class Overlord {
    private static Overlord s_instance = null;

    public static void init() {
        if (s_instance != null) {
            throw new AssertionError("s_instance != null");
        }
        s_instance = new Overlord();
        s_instance.setup();
    }

    public static Overlord get() {
        if (s_instance == null) {
            throw new AssertionError("Overlord not initted!");
        }
        return s_instance;
    }

    public final AssetManager assetManager = new AssetManager();

    private Overlord() {
    }

    private void setupSprites() {
        for (Sprite sprite : Sprite.values()) {
            assetManager.load(sprite.filePath, Texture.class);
        }
    }

    private void setupSFX() {
        for (SFX effect : SFX.values()) {
            assetManager.load(effect.filePath, Sound.class);
        }
    }

    private void setup() {
        setupSprites();
        setupSFX();
    }

    public boolean load() {
        return assetManager.update(1000 / 30);
    }
}
