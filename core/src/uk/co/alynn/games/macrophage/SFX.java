package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.audio.Sound;

public enum SFX {
    CLICK("click", 0.7f),
    SELECT("select", 0.5f),
    SLIME_BIRTH("slime-birth", 1.0f),
    SLIME_DEATH("slime-death", 1.0f),
    SLIME_MOVE("slime-move", 1.0f),
    UHUH("uhuh", 0.6f),
    VIRUS_BIRTH("virus-birth", 0.3f),
    VIRUS_DEATH("virus-death", 0.4f),
    VIRUS_MOVE("virus-move", 0.4f);

    public final String filePath;
    private final float vol;

    private SFX(String name, float volume) {
        filePath = "sfx/" + name + ".wav";
        vol = volume;
    }

    public void play() {
        Sound snd = Overlord.get().assetManager.get(filePath, Sound.class);
        snd.play(vol);
    }
}
