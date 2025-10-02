package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;
import sk.tuke.kpi.oop.game.Reactor;


public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible {
    private int temperature;
    private int damage;
    private Animation reactorExtinguishedAnimation = new Animation("path/to/reactor_extinguished.png");

    public FireExtinguisher() {

        super(1);
        setAnimation(new Animation("sprites/extinguisher.png"));
        this.damage = 0;
        this.temperature = 0;
    }

    public void useOn(Reactor reactor) {
        if (getRemainingUses() > 0 && reactor != null) {
            reactor.extinguishWith(this);
            use();
        }
    }

    public void extinguish (FireExtinguisher fireExtinguisher) {
        if (fireExtinguisher == null || damage < 100) {
            return;
        }
        if (damage >= 100) {
            temperature = 4000;
        }

        setAnimation(reactorExtinguishedAnimation);
    }

    @Override
    public void useWith(Reactor reactor) {
        if (reactor != null && getRemainingUses() > 0) {
            extinguish(this);
            use();
        }
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
