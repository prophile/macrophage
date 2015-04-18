package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MacrophageGame extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer renderer;
    GameMode mode;
    Viewport viewport;

    @Override
    public void create () {
        Overlord.init();
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        mode = new LoadingMode();
        viewport = new FitViewport(1024.0f, 640.0f);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void render () {
        GameMode newMode = mode.think(Gdx.graphics.getDeltaTime());
        if (newMode != mode) {
            mode.deactivate();
            mode = newMode;
            mode.activate();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        mode.render(batch, renderer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
