package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public final class ConclusionMode implements GameMode {

    private static final float Y_HEIGHT = 320.0f;
    private static final float SPACING = 100.0f;
    private static final float PERIOD = 1.5f;
    private boolean advance = false;
    public final Side winners;
    private float angle = 0.0f;
    private static final float PHASE = 1.0f;
    private static final float Y_VARIANCE = 40.0f;

    public ConclusionMode(Side winners) {
        this.winners = winners;
    }

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        Sprite sprite;
        switch (winners) {
        case SLIMES:
            sprite = Sprite.SLIME;
            break;
        case VIRUSES:
            sprite = Sprite.VIRUS;
            break;
        default:
            throw new RuntimeException("Beef");
        }
        for (int i = -6; i <= 6; ++i) {
            sprite.draw(spriteBatch, 512.0f - SPACING*i, (float) (Y_HEIGHT + Y_VARIANCE*Math.cos(angle + PHASE*i)));
        }
        spriteBatch.end();
    }

    @Override
    public GameMode think(float dt) {
        angle += dt * Math.PI * 2.0f * (1.0f / PERIOD);
        if (advance) {
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

    @Override
    public void mouseMove(int x, int y) {
    }

    @Override
    public void mouseClick(int x, int y) {
        if (advance)
            return;
        SFX.CLICK.play();
        advance = true;
    }

}
