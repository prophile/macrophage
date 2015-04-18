package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.audio.Sound;

public enum SFX {
    CLICK("click", 0.7f),
    SELECT("select", 0.02f),
    SLIME_BIRTH("slime-birth", 0.1f),
    SLIME_DEATH("slime-death", 0.1f),
    SLIME_MOVE("slime-move", 0.1f),
    UHUH("uhuh", 0.06f),
    VIRUS_BIRTH("virus-birth", 0.03f),
    VIRUS_DEATH("virus-death", 0.2f),
    VIRUS_MOVE("virus-move", 0.04f);

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
