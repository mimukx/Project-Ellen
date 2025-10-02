package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean isOn;
    private boolean isPowered;
    private Animation lightOnAnimation;
    private Animation lightOffAnimation;



    public Light() {
        this.isOn = false;
        this.isPowered = false;
        this.lightOnAnimation = new Animation("sprites/light_on.png", 16, 16);
        this.lightOffAnimation = new Animation("sprites/light_off.png", 16, 16);
        setAnimation(lightOffAnimation);
    }



    public void turnOn() {
        isOn = true;
        updateAnimation();

    }

    public void turnOff() {
        isOn = false;
        updateAnimation();

    }

    public void toggle() {
        this.isOn = !this.isOn;
        updateAnimation();
    }

    public void setElectricityFlow(boolean isPowered) {
        this.isPowered = isPowered;
        updateAnimation();
    }

    private void updateAnimation() {
        if (isOn && isPowered) {
            setAnimation(lightOnAnimation);
        } else {
            setAnimation(lightOffAnimation);
        }
    }




    @Override
    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void setPowered(boolean power) {
        this.isPowered = power;
        updateAnimation();
    }


}

