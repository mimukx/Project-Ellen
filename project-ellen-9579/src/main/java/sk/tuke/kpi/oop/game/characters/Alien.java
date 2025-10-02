package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable, Alive, Enemy, Behaviour<Alien> {

    private Health health;
    private int speed;
    private Behaviour<Alien> behaviour;

    public Alien() {
        super("alien");
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        health = new Health(100);
        speed = 1;
    }

    public Alien(int initHealth, Behaviour<Alien> behaviour) {
        super("alien");
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        health = new Health(initHealth);
        this.behaviour = behaviour;
        speed = 1;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public Alien(int initHealth, String name) {
        super(name);
        setAnimation(new Animation("sprites/alien.png", 32, 32, 0.1f));
        health = new Health(initHealth);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void setUp(Alien actor) {

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        if (behaviour != null) {
            behaviour.setUp(this);
        }

        new Loop<>(new Invoke<>(() -> {
            getScene().getActors().stream()
                .filter(actor -> actor instanceof Alive && !(actor instanceof Enemy))
                .filter(this::intersects)
                .forEach(actor -> drainHealth((Alive) actor, 10));
        })).scheduleFor(this);
    }


    public void act(float deltaTime) {
        getScene().getActors().stream()
            .filter(actor -> actor instanceof Alive && !(actor instanceof Enemy))
            .filter(this::intersects)
            .forEach(actor -> drainHealth((Alive) actor, 10));
    }

    public void drainHealth(@NotNull Alive actor, int amount) {
        if (amount > 0 && actor.getHealth() != null) {
            actor.getHealth().drain(amount);
        }
    }

}
