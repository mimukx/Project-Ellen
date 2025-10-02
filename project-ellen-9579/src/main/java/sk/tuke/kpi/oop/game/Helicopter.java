package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Helicopter extends AbstractActor {
    private boolean active;

    public Helicopter() {
        Animation helicopterAnimation = new Animation("sprites/heli.png", 64, 64, 0.1f);
        setAnimation(helicopterAnimation);
        active = false;
    }

    public void activateSearchAndDestroy() {
        active = true;
    }

    protected void searchAndDestroy() {
        if (!active) return;

        Scene scene = getScene();
        if (scene == null) return;

        Actor player = scene.getFirstActorByType(Player.class);
        if (player == null) return;

        if (intersects(player)) {
            int currentEnergy = ((Player) player).getEnergy();
            ((Player) player).setEnergy(currentEnergy - 1);
        }
    }


    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::searchAndDestroy)).scheduleFor(this);
    }
}


