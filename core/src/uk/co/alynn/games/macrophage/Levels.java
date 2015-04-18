package uk.co.alynn.games.macrophage;

public abstract class Levels {
    public static Simulation firstLevel() {
        final int GRID_WIDTH = 8;
        final int GRID_HEIGHT = 5;

        return gridLevel(GRID_WIDTH, GRID_HEIGHT);
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

    private static Simulation gridLevel(int width, int height) {
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
