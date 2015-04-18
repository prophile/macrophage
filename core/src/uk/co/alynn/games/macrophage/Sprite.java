package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public enum Sprite {
    NODE("node"),
    NODE_HIGHLIGHT("node-highlight"),
    VIRUS("virus"),
    SLIME("slime");

    public final String filePath;

    private Sprite(String source) {
        filePath = "sprites/" + source + ".png";
    }

    public void draw(SpriteBatch batch, float x, float y, float r, float g, float b) {
        Texture tex = Overlord.get().assetManager.get(filePath, Texture.class);
        batch.setColor(r, g, b, 1.0f);
        batch.draw(tex, x - tex.getWidth()/2, y - tex.getHeight()/2);
    }

    public void draw(SpriteBatch spriteBatch, float x, float y) {
        draw(spriteBatch, x, y, 1.0f, 1.0f, 1.0f);
    }
}
