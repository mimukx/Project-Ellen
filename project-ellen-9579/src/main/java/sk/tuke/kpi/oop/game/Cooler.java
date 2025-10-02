package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private Reactor reactor;
    private Animation coolerAnimation;
    private boolean isOn;

    public Cooler(Reactor reactor) {
        this.reactor = reactor;
        this.isOn = false;
        this.coolerAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f);
        setAnimation(coolerAnimation);
    }

    public Reactor getReactor() {
        return reactor;
    }

    public void turnOn() {
        isOn = true;
        coolerAnimation.play();
    }

    public void turnOff() {
        isOn = false;
        coolerAnimation.pause();
    }

    public boolean isOn() {
        return isOn;
    }

    private void coolReactor() {
        if (reactor != null && isOn) {
            reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}


