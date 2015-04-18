package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameGameMode implements GameMode {
    private Simulation sim;

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.begin();
        for (int i = 0; i < sim.nodeCount(); ++i) {
            float x = sim.getNodeX(i);
            float y = sim.getNodeY(i);
            Sprite.NODE.draw(spriteBatch, x, y);
            if (sim.isOccupied(i)) {
                switch (sim.getSide(i)) {
                case VIRUSES:
                    Sprite.VIRUS.draw(spriteBatch, x, y);
                    break;
                case SLIMES:
                    Sprite.SLIME.draw(spriteBatch, x, y);
                    break;
                }
            }
        }
        spriteBatch.end();
    }

    @Override
    public GameMode think(float dt) {
        return this;
    }

    @Override
    public void activate() {
        sim = new Simulation(4);

        sim.setNodeX(0, 100.0f);
        sim.setNodeY(0, 100.0f);
        sim.setNodeX(1, 300.0f);
        sim.setNodeY(1, 100.0f);
        sim.setNodeX(2, 300.0f);
        sim.setNodeY(2, 300.0f);
        sim.setNodeX(3, 100.0f);
        sim.setNodeY(3, 300.0f);

        sim.setSide(0, Side.SLIMES);
        sim.setSide(2, Side.VIRUSES);

        sim.setLink(0, 0, 1);
        sim.setLink(0, 1, 3);
        sim.setLink(1, 0, 0);
        sim.setLink(1, 1, 2);
        sim.setLink(2, 0, 1);
        sim.setLink(2, 1, 3);
        sim.setLink(3, 0, 2);
        sim.setLink(3, 1, 0);
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
