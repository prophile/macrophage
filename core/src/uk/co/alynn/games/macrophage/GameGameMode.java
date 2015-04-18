package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameGameMode implements GameMode {

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
    }

    @Override
    public GameMode think(float dt) {
        return this;
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    @Override
    public void mouseMove(int x, int y) {
    }

    @Override
    public void mouseClick(int x, int y) {
    }

}
