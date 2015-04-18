package uk.co.alynn.games.macrophage;

import com.badlogic.gdx.math.MathUtils;

public abstract class SimAI {
    public static void decide(Side side, Simulation sim) {
        if (!sim.isExtant(side)) {
            throw new RuntimeException("AI on non-extant side");
        }
        Side opponent;
        switch (side) {
        case SLIMES:
            opponent = Side.VIRUSES;
            break;
        case VIRUSES:
            opponent = Side.SLIMES;
            break;
        default:
            throw new RuntimeException("what");
        }
        int baseOurCount = sim.countAlive(side);
        int baseTheirCount = sim.countAlive(opponent);
        int bestMoveSuperiority = -baseTheirCount;
        int bestMoveOurCount = baseOurCount;
        int bestMoveSRC = -1;
        int bestMoveDST = -1;
        // use those variables to decide on playstyle
        // if we have more units than they do, play aggressively
        boolean passivePlay = baseOurCount <= baseTheirCount;
        for (int source = 0; source < sim.nodeCount(); ++source) {
            if (!sim.isOccupied(source) || sim.getSide(source) != side)
                continue;
            for (int dest = 0; dest < sim.nodeCount(); ++dest) {
                if (!sim.isValidMove(source, dest))
                    continue;
                Simulation subsim = sim.softCopy();
                subsim.performMove(source, dest);
                if (!subsim.isExtant(side)) {
                    // this move immediately loses
                    continue;
                }
                if (!subsim.isExtant(opponent)) {
                    // this move wins us the game
                    sim.performMove(source, dest);
                    return;
                }
                // sometimes, just do this move because whatever
                if (MathUtils.randomBoolean(0.05f) && source != dest) { // don't randomly suicide tho
                    System.err.println("doing a bad move");
                    sim.performMove(source, dest);
                    return;
                }
                int simTheirCount = subsim.countAlive(opponent);
                int simOurCount = subsim.countAlive(side);
                int simSuperiority = simOurCount - simTheirCount;
                if (simSuperiority > bestMoveSuperiority) {
                    bestMoveSRC = source;
                    bestMoveDST = dest;
                    bestMoveSuperiority = simSuperiority;
                    bestMoveOurCount = simOurCount;
                } else if (simSuperiority == bestMoveSuperiority) {
                    if (passivePlay && simOurCount >= bestMoveOurCount) {
                        bestMoveSRC = source;
                        bestMoveDST = dest;
                        bestMoveSuperiority = simSuperiority;
                        bestMoveOurCount = simOurCount;
                    }
                }
            }
        }
        if (bestMoveSRC == -1 || bestMoveDST == -1) {
            throw new RuntimeException("Cannot decide on a move.");
        }
        sim.performMove(bestMoveSRC, bestMoveDST);
    }
}
