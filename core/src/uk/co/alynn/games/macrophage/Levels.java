package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.math.MathUtils;

public abstract class Levels {
    public static Simulation firstLevel() {
        final int GRID_WIDTH = 8;
        final int GRID_HEIGHT = 5;

        return gridLevel(GRID_WIDTH, GRID_HEIGHT);
    }

    public static Simulation circleLevel(int segments, int randomJoins) {
        Simulation sim = new Simulation(segments);
        final float RADIUS = 280.0f;
        for (int i = 0; i < segments; ++i) {
            float angle = (float) (Math.PI * 2.0f * i / segments);
            sim.setNodeX(i, (float) (512.0f + RADIUS*Math.cos(angle)));
            sim.setNodeY(i, (float) (320.0f + RADIUS*Math.sin(angle)));
            int prevI = (i + 1) % segments;
            int nextI = (i - 1) % segments;
            if (nextI == -1)
                nextI = segments - 1;
            sim.setLink(i, 0, prevI);
            sim.setLink(i, 1, nextI);
        }
        sim.setSide(0, Side.VIRUSES);
        sim.setSide(1, Side.VIRUSES);
        sim.setSide(segments/2, Side.SLIMES);
        sim.setSide(segments/2 + 1, Side.SLIMES);
        for (int i = 0; i < randomJoins;) {
            int src = MathUtils.random(segments - 1);
            int dst = MathUtils.random(segments - 1);
            int srcLink = MathUtils.random(2, 3);
            int dstLink = MathUtils.random(2, 3);
            if (src == dst) {
                continue;
            }
            if (sim.isConnected(src, dst)) {
                continue;
            }
            if (sim.getLink(src, srcLink) != -1) {
                continue;
            }
            if (sim.getLink(dst, dstLink) != -1) {
                continue;
            }
            sim.setLink(src, srcLink, dst);
            sim.setLink(dst, dstLink, src);
            ++i;
        }
        sim.tickSimulation();
        return sim;
    }

    public static Simulation diablo() {
        Simulation sim = new Simulation(9);
        sim.setNodeX(0, 500f);
        sim.setNodeY(0, 225f);
        sim.setNodeX(1, 330f);
        sim.setNodeY(1, 315f);
        sim.setSide(1, Side.SLIMES);
        sim.setNodeX(2, 380f);
        sim.setNodeY(2, 490f);
        sim.setSide(2, Side.SLIMES);
        sim.setNodeX(3, 590f);
        sim.setNodeY(3, 500f);
        sim.setSide(3, Side.VIRUSES);
        sim.setNodeX(4, 660f);
        sim.setNodeY(4, 310f);
        sim.setNodeX(5, 240f);
        sim.setNodeY(5, 155f);
        sim.setSide(5, Side.SLIMES);
        sim.setNodeX(6, 100f);
        sim.setNodeY(6, 350f);
        sim.setNodeX(7, 775f);
        sim.setNodeY(7, 140f);
        sim.setSide(7, Side.VIRUSES);
        sim.setNodeX(8, 925f);
        sim.setNodeY(8, 385f);
        sim.setSide(8, Side.VIRUSES);
        sim.setLink(6, 0, 1);
        sim.setLink(1, 0, 6);
        sim.setLink(5, 0, 1);
        sim.setLink(1, 1, 5);
        sim.setLink(6, 1, 5);
        sim.setLink(5, 1, 6);
        sim.setLink(8, 0, 4);
        sim.setLink(4, 0, 8);
        sim.setLink(7, 0, 4);
        sim.setLink(4, 1, 7);
        sim.setLink(8, 1, 7);
        sim.setLink(7, 1, 8);
        sim.setLink(1, 2, 0);
        sim.setLink(0, 0, 1);
        sim.setLink(4, 2, 0);
        sim.setLink(0, 1, 4);
        sim.setLink(5, 2, 0);
        sim.setLink(0, 2, 5);
        sim.setLink(7, 2, 0);
        sim.setLink(0, 3, 7);
        sim.setLink(1, 3, 2);
        sim.setLink(2, 0, 1);
        sim.setLink(4, 3, 3);
        sim.setLink(3, 0, 4);
        sim.setLink(2, 1, 3);
        sim.setLink(3, 1, 2);
        sim.setLink(6, 2, 2);
        sim.setLink(2, 2, 6);
        sim.setLink(8, 2, 3);
        sim.setLink(3, 2, 8);
        sim.tickSimulation();
        return sim;
    }

    public static Simulation heimdall() {
        Simulation sim = new Simulation(14);
        sim.setNodeX(0, 170f);
        sim.setNodeY(0, 235f);
        sim.setNodeX(1, 160f);
        sim.setNodeY(1, 375f);
        sim.setNodeX(2, 390f);
        sim.setNodeY(2, 510f);
        sim.setNodeX(3, 515f);
        sim.setNodeY(3, 510f);
        sim.setNodeX(4, 660f);
        sim.setNodeY(4, 510f);
        sim.setSide(4, Side.VIRUSES);
        sim.setNodeX(5, 885f);
        sim.setNodeY(5, 405f);
        sim.setNodeX(6, 865f);
        sim.setNodeY(6, 205f);
        sim.setNodeX(7, 330f);
        sim.setNodeY(7, 125f);
        sim.setSide(7, Side.SLIMES);
        sim.setNodeX(8, 535f);
        sim.setNodeY(8, 130f);
        sim.setNodeX(9, 680f);
        sim.setNodeY(9, 125f);
        sim.setNodeX(10, 395f);
        sim.setNodeY(10, 310f);
        sim.setNodeX(11, 625f);
        sim.setNodeY(11, 310f);
        sim.setNodeX(12, 105f);
        sim.setNodeY(12, 110f);
        sim.setSide(12, Side.SLIMES);
        sim.setNodeX(13, 915f);
        sim.setNodeY(13, 530f);
        sim.setSide(13, Side.VIRUSES);
        sim.setLink(1, 0, 0);
        sim.setLink(0, 0, 1);
        sim.setLink(1, 1, 10);
        sim.setLink(10, 0, 1);
        sim.setLink(1, 2, 2);
        sim.setLink(2, 0, 1);
        sim.setLink(0, 1, 7);
        sim.setLink(7, 0, 0);
        sim.setLink(6, 0, 11);
        sim.setLink(11, 0, 6);
        sim.setLink(6, 1, 9);
        sim.setLink(9, 0, 6);
        sim.setLink(5, 0, 4);
        sim.setLink(4, 0, 5);
        sim.setLink(10, 1, 8);
        sim.setLink(8, 0, 10);
        sim.setLink(11, 1, 8);
        sim.setLink(8, 1, 11);
        sim.setLink(10, 2, 3);
        sim.setLink(3, 0, 10);
        sim.setLink(3, 1, 11);
        sim.setLink(11, 2, 3);
        sim.setLink(3, 2, 2);
        sim.setLink(2, 1, 3);
        sim.setLink(3, 3, 4);
        sim.setLink(4, 1, 3);
        sim.setLink(7, 1, 8);
        sim.setLink(8, 2, 7);
        sim.setLink(8, 3, 9);
        sim.setLink(9, 1, 8);
        sim.setLink(0, 2, 12);
        sim.setLink(12, 0, 0);
        sim.setLink(12, 1, 7);
        sim.setLink(7, 2, 12);
        sim.setLink(13, 0, 4);
        sim.setLink(4, 2, 13);
        sim.setLink(5, 1, 13);
        sim.setLink(13, 1, 5);
        sim.setLink(10, 3, 2);
        sim.setLink(2, 2, 10);
        sim.setLink(9, 2, 11);
        sim.setLink(11, 3, 9);
        sim.setLink(6, 2, 5);
        sim.setLink(5, 2, 6);
        sim.tickSimulation();
        return sim;
    }

    public static Simulation sketchedLevel() {
        Simulation sim = new Simulation(11);
        sim.setNodeX(0, 140f);
        sim.setNodeY(0, 330f);
        sim.setSide(0, Side.VIRUSES);
        sim.setNodeX(1, 310f);
        sim.setNodeY(1, 220f);
        sim.setNodeX(2, 295f);
        sim.setNodeY(2, 420f);
        sim.setSide(2, Side.VIRUSES);
        sim.setNodeX(3, 535f);
        sim.setNodeY(3, 100f);
        sim.setNodeX(4, 515f);
        sim.setNodeY(4, 330f);
        sim.setNodeX(5, 510f);
        sim.setNodeY(5, 535f);
        sim.setNodeX(6, 235f);
        sim.setNodeY(6, 90f);
        sim.setNodeX(7, 795f);
        sim.setNodeY(7, 550f);
        sim.setNodeX(8, 695f);
        sim.setNodeY(8, 415f);
        sim.setNodeX(9, 690f);
        sim.setNodeY(9, 215f);
        sim.setSide(9, Side.SLIMES);
        sim.setNodeX(10, 885f);
        sim.setNodeY(10, 320f);
        sim.setSide(10, Side.SLIMES);
        sim.setLink(0, 0, 1);
        sim.setLink(1, 0, 0);
        sim.setLink(0, 1, 2);
        sim.setLink(2, 0, 0);
        sim.setLink(1, 1, 2);
        sim.setLink(2, 1, 1);
        sim.setLink(10, 0, 9);
        sim.setLink(9, 0, 10);
        sim.setLink(10, 1, 8);
        sim.setLink(8, 0, 10);
        sim.setLink(9, 1, 8);
        sim.setLink(8, 1, 9);
        sim.setLink(1, 2, 3);
        sim.setLink(3, 0, 1);
        sim.setLink(1, 3, 4);
        sim.setLink(4, 0, 1);
        sim.setLink(2, 2, 5);
        sim.setLink(5, 0, 2);
        sim.setLink(8, 2, 5);
        sim.setLink(5, 1, 8);
        sim.setLink(8, 3, 4);
        sim.setLink(4, 1, 8);
        sim.setLink(9, 2, 3);
        sim.setLink(3, 1, 9);
        sim.setLink(3, 2, 4);
        sim.setLink(4, 2, 3);
        sim.setLink(5, 2, 4);
        sim.setLink(4, 3, 5);
        sim.setLink(3, 3, 6);
        sim.setLink(6, 0, 3);
        sim.setLink(5, 3, 7);
        sim.setLink(7, 0, 5);
        sim.tickSimulation();
        return sim;

    }

    public static Simulation gridLevel(int width, int height) {
        Simulation sim = new Simulation(width*height);

        final float xfactor = 1024.0f / (width + 1);
        final float yfactor = 640.0f / (height + 1);

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int ix = (width*y) + x;
                sim.setNodeX(ix, xfactor + xfactor*x);
                sim.setNodeY(ix, yfactor + yfactor*y);
                if (x - 1 >= 0) {
                    sim.setLink(ix, 0, ix - 1);
                }
                if (x + 1 < width) {
                    sim.setLink(ix, 1, ix + 1);
                }
                if (y - 1 >= 0) {
                    sim.setLink(ix, 2, ix - width);
                }
                if (y + 1 < height) {
                    sim.setLink(ix, 3, ix + width);
                }
            }
        }

        sim.setSide(0, Side.SLIMES);
        sim.setSide(1, Side.SLIMES);
        sim.setSide(width*height - 2, Side.VIRUSES);
        sim.setSide(width*height - 1, Side.VIRUSES);

        sim.tickSimulation();

        return sim;
    }
}
