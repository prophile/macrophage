package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface GameMode {
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer);
    public GameMode think(float dt);

    public void activate();
    public void deactivate();

    public void mouseMove(int x, int y);
    public void mouseClick(int x, int y);
}
