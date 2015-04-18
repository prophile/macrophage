package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MacrophageGame extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    ShapeRenderer renderer;
    GameMode mode;
    Viewport viewport;

    @Override
    public void create () {
        Overlord.init();
        Gdx.input.setInputProcessor(this);
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

    private int lastMouseXWS = 512;
    private int lastMouseYWS = 320;

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        mouseMoved(screenX, screenY);
        mode.mouseClick(lastMouseXWS, lastMouseYWS);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    private final Vector2 projectionVector = new Vector2();

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        projectionVector.x = screenX;
        projectionVector.y = screenY;
        Vector2 projected = viewport.unproject(projectionVector);
        lastMouseXWS = (int)projected.x;
        lastMouseYWS = (int)projected.y;
        mode.mouseMove(lastMouseXWS, lastMouseYWS);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
