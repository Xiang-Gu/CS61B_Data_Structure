package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;
import static java.lang.Math.min;
import static java.lang.StrictMath.max;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to e.
     */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /** return the color of the clorus, which is always
     * r = 34, g = 0, b = 231.
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r,g,b);
    }

    /**
     * Clorus attacks creature c and gains its energy.
     */
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Clorus lose 0.03 units of energy when it moves.
     */
    public void move() {
        energy -= 0.03;
    }


    /**
     * Clorus lose 0.01 units of energy when it moves.
     */
    public void stay() {
        energy -= 0.01;
    }

    /**
     * Clorus and their offspring each get 50% of the energy.
     * Returns a baby Clorus.
     */
    public Clorus replicate() {
        Clorus clorusJr = new Clorus(energy / 2);
        energy /= 2;
        return clorusJr;
    }

    /** Cloruses should obey exactly the following behavioral rules:
     * 1).If there are no empty squares, the Clorus will STAY (even if there are Plips nearby they could attack since plip squares do not count as empty squares).
     * 2).Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3).Otherwise, if the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
     * 4).Otherwise, the Clorus will MOVE to a random empty square.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeightbors = new ArrayDeque<>();

        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.add(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                plipNeightbors.add(d);
            }
        }

        // Rule 1
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (plipNeightbors.size() != 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeightbors));
        }

        // Rule 3
        if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
