package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.actions.Invoke;

public class SmartCooler extends Cooler {

    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    private void checkTemperature() {
        Reactor reactor = getReactor();
        if (reactor == null) return;

        int temperature = reactor.getTemperature();
        if (temperature > 2500 && !isOn()) {
            turnOn();
        } else if (temperature < 1500 && isOn()) {
            turnOff();
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::checkTemperature)).scheduleFor(this);
    }
}

