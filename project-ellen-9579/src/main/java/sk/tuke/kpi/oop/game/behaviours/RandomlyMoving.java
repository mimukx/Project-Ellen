package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable>{

    private Disposable disposable;

    public RandomlyMoving() {

    }

    @Override
    public void setUp(Movable actor) {
        if (actor != null) {
            loopSetUp(actor);
        }
    }

    private Direction randomMove() {
        int random = new Random().nextInt(4);
        return helpDirection(random);
    }

    private Direction helpDirection(int value) {
        switch (value) {
            case 0:
                return Direction.EAST;
            case 1:
                return Direction.SOUTH;
            case 2:
                return Direction.WEST;
            case 3:
                return Direction.NORTH;
            default:
                return Direction.NONE;
        }
    }

    private void loopSetUp(Movable actor) {
        new Loop<>(
            new ActionSequence<>(
                new Wait<>(1),
                new Invoke<Actor>(() -> {
                    if (disposable != null)
                        disposable.dispose();
                    disposable = new Move<>(randomMove(), 99999999f).scheduleFor(actor);
                })
            )
        ).scheduleFor(actor);
    }
}
