package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MenuMode implements GameMode {

    private boolean advance = false;
    private float fadeLevel = 0.0f;

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.circle(512.0f, 320.0f, 100.0f);
        shapeRenderer.setColor(0.0f, 0.0f, 0.0f, fadeLevel);
        shapeRenderer.rect(0.0f, 0.0f, 1024.0f, 640.0f);
        shapeRenderer.end();
    }

    @Override
    public GameMode think(float dt) {
        if (advance && fadeLevel >= 1.0f) {
            return new GameGameMode();
        } else {
            if (advance) {
                fadeLevel += dt * 0.45f;
            }
            return this;
        }
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
        if (advance)
            return;
        advance = true;
        SFX.CLICK.play();
    }

}
