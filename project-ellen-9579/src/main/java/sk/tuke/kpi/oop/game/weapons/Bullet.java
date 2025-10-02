package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Bullet extends AbstractActor implements Fireable {

    public Bullet(){
        Animation animation = new Animation("sprites/bullet.png", 16, 16);
        setAnimation(animation);
    }

    @Override
    public int getSpeed() {
        return 4;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public void collidedWithWall() {
        if(getScene() != null){
            getScene().cancelActions(this);
            getScene().removeActor(this);
        }
    }


    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::check)
            )
        ).scheduleFor(this);

    }



    private void check(){
        for (Actor actor : getScene().getActors()) {
            if (actor.intersects(this) && !(actor instanceof Ripley) && actor instanceof Alive) {
                ((Alive) actor).getHealth().drain(50);
                if(((Alive) actor).getHealth().isDead()){
                    getScene().removeActor(actor);
                }
                getScene().removeActor(this);
            }
        }
    }
}
