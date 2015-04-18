package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameGameMode implements GameMode {
    private Simulation sim;
    private int selected = -1;
    private int hovering = -1;
    private Side playerSide = Side.VIRUSES;

    private float DISTANCE_SQUARED = 44.0f*44.0f;

    @Override
    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        shapeRenderer.begin(ShapeType.Line);
        for (int i = 0; i < sim.nodeCount(); ++i) {
            float srcX = sim.getNodeX(i);
            float srcY = sim.getNodeY(i);
            for (int j = 0; j < 4; ++j) {
                int link = sim.getLink(i, j);
                if (link == -1)
                    continue;
                if (link > i)
                    continue; // only draw the link once
                float dstX = sim.getNodeX(link);
                float dstY = sim.getNodeY(link);
                shapeRenderer.line(srcX, srcY, dstX, dstY);
            }
        }
        shapeRenderer.end();
        spriteBatch.begin();
        for (int i = 0; i < sim.nodeCount(); ++i) {
            float x = sim.getNodeX(i);
            float y = sim.getNodeY(i);
            if (selected == i) {
                Sprite.NODE_HIGHLIGHT.draw(spriteBatch, x, y, 0.0f, 1.0f, 1.0f);
            } else if (hovering == i) {
                if (selected == -1) {
                    if (sim.isOccupied(hovering) && sim.getSide(hovering) == playerSide) {
                        Sprite.NODE_HIGHLIGHT.draw(spriteBatch, x, y, 0.6f, 1.0f, 1.0f);
                    }
                } else if (sim.isValidMove(selected, i)) {
                    Sprite.NODE_HIGHLIGHT.draw(spriteBatch, x, y, 0.0f, 1.0f, 0.0f);
                } else {
                    Sprite.NODE_HIGHLIGHT.draw(spriteBatch, x, y, 1.0f, 0.0f, 0.0f);
                }
            } else if (selected != -1 && sim.isValidMove(selected, i)) {
                Sprite.NODE_HIGHLIGHT.draw(spriteBatch, x, y, 1.0f, 0.0f, 1.0f);
            }
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
        sim = new Simulation(9);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int ix = (3*y) + x;
                sim.setNodeX(ix, 100.0f + 200.0f*x);
                sim.setNodeY(ix, 100.0f + 200.0f*y);
                if (x - 1 >= 0) {
                    sim.setLink(ix, 0, ix - 1);
                }
                if (x + 1 < 3) {
                    sim.setLink(ix, 1, ix + 1);
                }
                if (y - 1 >= 0) {
                    sim.setLink(ix, 2, ix - 3);
                }
                if (y + 1 < 3) {
                    sim.setLink(ix, 3, ix + 3);
                }
            }
        }

        sim.setSide(0, Side.SLIMES);
        sim.setSide(1, Side.SLIMES);
        sim.setSide(7, Side.VIRUSES);
        sim.setSide(8, Side.VIRUSES);

        sim.tickSimulation();
    }

    @Override
    public void deactivate() {
    }

    @Override
    public void mouseMove(int x, int y) {
        int nextHovering = -1;
        for (int i = 0; i < sim.nodeCount(); ++i) {
            float nx = sim.getNodeX(i);
            float ny = sim.getNodeY(i);
            float distSquared = (nx - x)*(nx - x) + (ny - y)*(ny - y);
            if (distSquared <= DISTANCE_SQUARED) {
                nextHovering = i;
                break;
            }
        }
        if (nextHovering != hovering) {
            hovering = nextHovering;
        }
    }

    @Override
    public void mouseClick(int x, int y) {
        if (selected >= 0 && hovering == -1) {
            selected = -1;
        } else if (selected == -1 && hovering == -1) {
            return;
        } else if (selected == -1 && hovering >= 0) {
            if (sim.isOccupied(hovering) && sim.getSide(hovering) == playerSide) {
                selected = hovering;
                SFX.SELECT.play();
            }
        } else if (sim.isValidMove(selected, hovering)) {
            sim.performMove(selected, hovering);
            selected = -1;
        } else {
            SFX.UHUH.play();
        }
    }

}
