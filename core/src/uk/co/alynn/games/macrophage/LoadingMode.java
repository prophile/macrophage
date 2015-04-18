package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LoadingMode implements GameMode {

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(0.0f, 0.0f, 1024.0f * Overlord.get().assetManager.getProgress(), 60.0f);
        shapeRenderer.end();
    }

    @Override
    public GameMode think(float dt) {
        boolean loaded = Overlord.get().load();
        if (loaded) {
            return new MenuMode();
        } else {
            return this;
        }
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

}
