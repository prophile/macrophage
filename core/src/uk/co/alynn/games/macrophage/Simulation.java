package uk.co.alynn.games.macrophage;

public final class Simulation {
    private static final boolean ALLOW_SUICIDE = true;
    private final float[] positions;
    private final int[] states;
    private boolean didBirthVirus;
    private boolean didBirthSlime;
    private final boolean isSoftCopy;

    public float userdata;

    private Simulation(Simulation source) {
        positions = source.positions;
        states = new int[source.states.length];
        System.arraycopy(source.states, 0, states, 0, states.length);
        userdata = source.userdata;
        isSoftCopy = true;
    }

    public Simulation(int nodes) {
        isSoftCopy = false;
        positions = new float[nodes*2];
        states = new int[nodes];
        for (int i = 0; i < nodes; ++i) {
            states[i] = 1073741820;
            positions[2*i] = 0.0f;
            positions[2*i + 1] = 0.0f;
        }
    }

    public Simulation softCopy() {
        return new Simulation(this);
    }

    public int countAlive(Side side) {
        int count = 0;
        for (int i = 0; i < nodeCount(); ++i) {
            if (!isOccupied(i))
                continue;
            if (getSide(i) == side)
                ++count;
        }
        return count;
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
        return countAlive(side) > 0;
    }

    public boolean anyValidMoves(Side side) {
        for (int i = 0; i < nodeCount(); ++i) {
            if (!isOccupied(i))
                continue;
            if (getSide(i) != side)
                continue;
            for (int j = 0; j < nodeCount(); ++j) {
                if (isValidMove(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidMove(int nodeA, int nodeB) {
        if (nodeA == nodeB) { // suicide moves
            if (ALLOW_SUICIDE) {
                return isOccupied(nodeA) && getSurrounder(nodeA) != getSide(nodeA);
            } else {
                return false;
            }
        }
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
        boolean destOccupied = isOccupied(dest);
        if (destOccupied && !isSoftCopy) {
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
        if (source != dest) {
            setSide(dest, getSide(source));
        }
        if (destOccupied) {
            clearOccupation(source);
        } else {
            switch (getSide(dest)) {
            case SLIMES:
                setSide(source, Side.VIRUSES);
                break;
            case VIRUSES:
                setSide(source, Side.SLIMES);
                break;
            }
        }
        // drive the automatic updates
        tickSimulation();
        if (!didBirthVirus && !didBirthSlime && movementFX != null && !isSoftCopy)
            movementFX.play();
    }

    public void tickSimulation() {
        sanityCheckLinks();
        didBirthVirus = false;
        didBirthSlime = false;
        while (runOneTick());
        if (didBirthVirus && !isSoftCopy)
            SFX.VIRUS_BIRTH.play();
        if (didBirthSlime && !isSoftCopy)
            SFX.SLIME_BIRTH.play();
    }

    private void sanityCheckLinks() {
        boolean failures = false;
        for (int i = 0; i < nodeCount(); ++i) {
            for (int j = 0; j < nodeCount(); ++j) {
                boolean forwardLink = isConnected(i, j);
                boolean reverseLink = isConnected(j, i);
                if (forwardLink && !reverseLink) {
                    System.err.println("MISSING LINK: " + j + " -> " + i);
                    failures = true;
                }
                if (!forwardLink && reverseLink) {
                    System.err.println("MISSING LINK: " + i + " -> " + j);
                    failures = true;
                }
                if ((i == j) && (forwardLink || reverseLink)) {
                    System.err.println("SELF LINK: " + i);
                    failures = true;
                }
            }
        }
        if (failures) {
            throw new RuntimeException("link table is broke");
        }
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
