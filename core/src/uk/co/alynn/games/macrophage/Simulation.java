package uk.co.alynn.games.macrophage;

public final class Simulation {
    private final float[] positions;
    private final int[] states;
    private boolean didBirthVirus;
    private boolean didBirthSlime;

    public Simulation(int nodes) {
        positions = new float[nodes*2];
        states = new int[nodes];
        for (int i = 0; i < nodes; ++i) {
            states[i] = 1073741820;
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

    public boolean isValidMove(int nodeA, int nodeB) {
        if (nodeA == nodeB)
            return false; // no null-moves
        if (!isConnected(nodeA, nodeB))
            return false; // unconnected nodes
        if (!isOccupied(nodeA))
            return false; // can't move from an empty slot
        if (!isOccupied(nodeB))
            return true; // moving to empty territory
        if (getSide(nodeA) == getSide(nodeB))
            return false; // self-squashing
        int surroundingEnemies = 0;
        for (int j = 0; j < 4; ++j) {
            int link = getLink(nodeB, j);
            if (link == -1)
                continue;
            if (!isOccupied(link))
                continue;
            if (getSide(link) != getSide(nodeB))
                ++surroundingEnemies;
        }
        return surroundingEnemies >= 2; // need at least 2 enemies to take over
    }

    private boolean isConnected(int nodeA, int nodeB) {
        for (int i = 0; i < 4; ++i) {
            if (getLink(nodeA, i) == nodeB)
                return true;
        }
        return false;
    }

    public void performMove(int source, int dest) {
        if (!isValidMove(source, dest)) {
            throw new AssertionError("not a valid move!");
        }
        SFX movementFX = null;
        if (isOccupied(dest)) {
            switch (getSide(dest)) {
            case SLIMES:
                SFX.SLIME_DEATH.play();
                break;
            case VIRUSES:
                SFX.VIRUS_DEATH.play();
                break;
            }
        } else {
            switch (getSide(source)) {
            case SLIMES:
                movementFX = SFX.SLIME_MOVE;
                break;
            case VIRUSES:
                movementFX = SFX.VIRUS_MOVE;
                break;
            }
        }
        setSide(dest, getSide(source));
        clearOccupation(source);
        // drive the automatic updates
        tickSimulation();
        if (!didBirthVirus && !didBirthSlime && movementFX != null)
            movementFX.play();
    }

    public void tickSimulation() {
        didBirthVirus = false;
        didBirthSlime = false;
        while (runOneTick());
        if (didBirthVirus)
            SFX.VIRUS_BIRTH.play();
        if (didBirthSlime)
            SFX.SLIME_BIRTH.play();
    }

    private boolean runOneTick() {
        boolean anyChanges = false;
        for (int i = 0; i < nodeCount(); ++i) {
            Side surrounders = getSurrounder(i);
            if (surrounders != null) {
                if (!isOccupied(i) || getSide(i) != surrounders) {
                    setSide(i, surrounders);
                    anyChanges = true;
                    switch (surrounders) {
                    case SLIMES:
                        didBirthSlime = true;
                        break;
                    case VIRUSES:
                        didBirthVirus = true;
                        break;
                    }
                }
            }
        }
        return anyChanges;
    }

    private Side getSurrounder(int i) {
        Side lastSeenSide = null;
        for (int j = 0; j < 4; ++j) {
            int link = getLink(i, j);
            if (link == -1)
                continue;
            if (!isOccupied(link))
                return null;
            Side linkSide = getSide(link);
            if (lastSeenSide != null && linkSide != lastSeenSide)
                return null;
            lastSeenSide = linkSide;
        }
        return lastSeenSide;
    }
}
