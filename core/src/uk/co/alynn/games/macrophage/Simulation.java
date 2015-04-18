package uk.co.alynn.games.macrophage;

public final class Simulation {
    private final float[] positions;
    private final int[] states;

    public Simulation(int nodes) {
        positions = new float[nodes*2];
        states = new int[nodes];
        for (int i = 0; i < nodes; ++i) {
            states[i] = 0;
            positions[2*i] = 0.0f;
            positions[2*i + 1] = 0.0f;
        }
    }

    public float getNodeX(int node) {
        return positions[2*node + 0];
    }

    public void setNodeX(int node, float x) {
        positions[2*node + 0] = x;
    }

    public float getNodeY(int node) {
        return positions[2*node + 1];
    }

    public void setNodeY(int node, float y) {
        positions[2*node + 1] = y;
    }

    public boolean isOccupied(int node) {
        return (states[node] & 0x3) != 0;
    }

    public Side getSide(int node) {
        return Side.values()[(states[node] & 0x03) - 1];
    }

    public int getLink(int node, int ix) {
        int shift = ix * 7 + 2;
        int state = states[node] >> shift;
        state &= 0x7F;
        return state != 127 ? state : -1;
    }

    public void setLink(int node, int ix, int otherNode) {
        int shift = ix * 7 + 2;
        int state = states[node];
        state &= ~(0x7F << shift);
        state |= otherNode << shift;
        states[node] = state;
    }

    public void clearOccupation(int node) {
        states[node] &= ~0x03;
    }

    public void setSide(int node, Side side) {
        int state = states[node];
        state &= ~0x03;
        state |= side.ordinal() + 1;
        states[node] = state;
    }

    public int nodeCount() {
        return states.length;
    }

    public boolean isExtant(Side side) {
        for (int i = 0; i < nodeCount(); ++i) {
            if (isOccupied(i) && getSide(i) == side)
                return true;
        }
        return false;
    }

    @SuppressWarnings("static-method")
    public boolean isValidMode(int nodeA, int nodeB) {
        return false;
    }
}
