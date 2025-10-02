package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;

public class Locker extends AbstractActor {
    private boolean isUsed;

    public Locker() {
        super();
        setAnimation(new Animation("sprites/locker.png", 16, 16));
        this.isUsed = false;
    }


    public void useWith(Actor actor) {
        if (!isUsed && actor instanceof Ripley) {
            isUsed = true;
            Hammer hammer = new Hammer();
            hammer.setPosition(getPosX() + 16, getPosY());
            getScene().addActor(hammer);
        } else if (isUsed) {
            System.out.println("The locker is empty.");
        }
    }


    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }
}
