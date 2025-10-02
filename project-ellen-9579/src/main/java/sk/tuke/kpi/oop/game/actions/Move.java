package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;


public class Move<A extends Movable> implements Action<A> {

    private A moveActor;
    private Direction direction;
    private boolean done;
    private  float duration;
    private boolean first;

    public Move(@NotNull Direction direction, float duration) {
        this(direction);
        this.duration = duration;
    }

    private Move(@NotNull Direction direction) {
        this.direction = direction;
        done = false;
        first = true;

    }

    @Nullable
    @Override
    public A getActor() {
        return moveActor;
    }

    @Override
    public void setActor(A movable) {
        this.moveActor =  movable;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void execute(float deltaTime) {

        if (!isDone()) {
            if (first) {
                moveActor.startedMoving(direction);
                first = false;
            }
            moveRealise(deltaTime);
            if (duration > 0) {
                moveActor.setPosition(moveActor.getPosX() + direction.getDx() * moveActor.getSpeed(), moveActor.getPosY() + direction.getDy() * moveActor.getSpeed());

                if ((moveActor.getScene()).getMap().intersectsWithWall(moveActor)) {
                    moveActor.setPosition(moveActor.getPosX() - direction.getDx() * moveActor.getSpeed(), moveActor.getPosY() - direction.getDy() * moveActor.getSpeed());
                    moveActor.collidedWithWall();
                }
                helpMove(deltaTime);
            } else {
                stop();
            }
        }
    }

    @Override
    public void reset() {
        moveActor.stoppedMoving();
        done = false;
        first = false;
        duration=0;
    }

    public void stop(){
        if (moveActor != null) {
            moveActor.stoppedMoving();
            done = true;
            first = false;
        }
    }

    private void moveRealise(float deltaTime){
        duration-=deltaTime;
    }
    private int helpMove(float deltaTime){
        float number = 0;
        if(deltaTime>0){
            number=+deltaTime;
        }else{
            number=-deltaTime;
        }
        return (int) number;
    }
}
