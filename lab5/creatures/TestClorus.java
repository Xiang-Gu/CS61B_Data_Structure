package creatures;

import huglife.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Color;
import java.util.HashMap;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(3.0);

        assertEquals("clorus", c.name());
        assertEquals(3.0, c.energy(), 0.01);

        c.move();
        assertEquals(2.97, c.energy(), 0.01);

        c.stay();
        assertEquals(2.96, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());

        Plip p = new Plip(2.0);
        c.attack(p);
        assertEquals(4.96, c.energy(), 0.01);
    }

    @Test
    public void testChoose() {
        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Plip());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // Empty adjacent space and plip. Attack one of them randomly!
        HashMap<Direction, Occupant> plipNearBy = new HashMap<Direction, Occupant>();
        plipNearBy.put(Direction.TOP, new Impassible());
        plipNearBy.put(Direction.BOTTOM, new Plip());
        plipNearBy.put(Direction.LEFT, new Impassible());
        plipNearBy.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(plipNearBy);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);

        // Empty adjacent space, no plip and Clorus' energy is >= 1.0. Replicate in a random empty direction!
        HashMap<Direction, Occupant> emptySurroudings = new HashMap<Direction, Occupant>();
        emptySurroudings.put(Direction.TOP, new Impassible());
        emptySurroudings.put(Direction.BOTTOM, new Empty());
        emptySurroudings.put(Direction.LEFT, new Impassible());
        emptySurroudings.put(Direction.RIGHT, new Empty());

        boolean fActual1 = false;
        boolean fActual2 = false;
        for (int i = 0; i < 30; i++) {
            actual = c.chooseAction(emptySurroudings);
            if (actual.equals(new Action(Action.ActionType.REPLICATE, Direction.RIGHT))) {
                fActual1 = true;
            } else if (actual.equals(new Action(Action.ActionType.REPLICATE, Direction.BOTTOM))) {
                fActual2 = true;
            }
        }
        assertEquals(fActual1, true);
        assertEquals(fActual2, true);

        // Empty adjacent space, no plip, and Clorus' energy is < 1.0. Move in a random empty direction!
        Clorus c2 = new Clorus(0.98);
        fActual1 = false;
        fActual2 = false;
        for (int i = 0; i < 30; i++) {
            actual = c2.chooseAction(emptySurroudings);
            if (actual.equals(new Action(Action.ActionType.MOVE, Direction.RIGHT))) {
                fActual1 = true;
            } else if (actual.equals(new Action(Action.ActionType.MOVE, Direction.BOTTOM))) {
                fActual2 = true;
            }
        }
        assertEquals(fActual1, true);
        assertEquals(fActual2, true);
    }
}
