package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private boolean activated;
    private float time;


    public TimeBomb(float time) {
        this.activated = false;
        this.time = time;
        setAnimation(new Animation("sprites/bomb.png"));
    }

    public void activate() {
        if (activated) return;
        this.activated = true;
        setAnimation(new Animation("sprites/bomb_activated.png"));

        new ActionSequence<>(
            new Wait<>(this.time),
            new Invoke<>(this::explode)
        ).scheduleFor(this);
    }

    public boolean isActivated() {
        return activated;
    }

    void explode() {

        setAnimation(new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE));

        new ActionSequence<>(
            new Wait<>(0.8f),
            new Invoke<>(() -> {
                if (getScene() != null) {
                    getScene().removeActor(this);
                }
            })
        ).scheduleFor(this);
    }

}


