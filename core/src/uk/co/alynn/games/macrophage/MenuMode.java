package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MenuMode implements GameMode {

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.circle(512.0f, 320.0f, 100.0f);
        shapeRenderer.end();
    }

    @Override
    public GameMode think(float dt) {
        return this;
    }

    @Override
    public void activate() {
        SFX.SELECT.play();
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
