package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class PowerSwitch extends AbstractActor {
    private Switchable device;

    public PowerSwitch(Switchable device) {
        this.device = device;
        Animation switchAnimation = new Animation("sprites/switch.png");
        setAnimation(switchAnimation);
    }

    public Switchable getDevice() {
        return device;
    }

    public void switchOn() {
        if (device != null) {
            device.turnOn();
            getAnimation().setTint(Color.WHITE);
        }
    }

    public void switchOff() {
        if (device != null) {
            device.turnOff();
            getAnimation().setTint(Color.GRAY);
        }
    }
}


