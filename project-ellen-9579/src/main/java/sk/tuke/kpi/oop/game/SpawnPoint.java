package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class SpawnPoint extends AbstractActor {
    private int maxAliens;
    private int createdAliens;
    private Disposable spawnLoop;

    public SpawnPoint(int maxAliens) {
        this.maxAliens = maxAliens;
        this.createdAliens = 0;
        setAnimation(new sk.tuke.kpi.gamelib.graphics.Animation("sprites/spawn.png", 32, 32, 0.1f));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        spawnLoop = new Loop<>(new Invoke<>(this::spawnAlien)).scheduleFor(this);
    }

    private void spawnAlien() {

        if (createdAliens >= maxAliens) {
            dispose();
            return;
        }

        Scene scene = getScene();
        if (scene == null) return;

        Actor ripley = scene.getFirstActorByType(Ripley.class);
        if (ripley == null || distanceTo(ripley) > 50) {
            return;
        }

        Alien alien = new Alien();
        scene.addActor(alien, getPosX(), getPosY());
        createdAliens++;


        new Wait<>(3).scheduleFor(this);
    }

    public void dispose() {
        if (spawnLoop != null) {
            spawnLoop.dispose();
            spawnLoop = null;
        }
    }

    private float distanceTo(Actor actor) {
        return (float) Math.hypot(actor.getPosX() - getPosX(), actor.getPosY() - getPosY());
    }
}
