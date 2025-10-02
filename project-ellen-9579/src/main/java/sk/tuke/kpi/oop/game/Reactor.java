package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.gamelib.Scene;

import java.util.ArrayList;
import java.util.List;


public class Reactor extends AbstractActor implements Repairable, Switchable{
    private boolean isOn;
    private int temperature;
    private int damage;
    private Light connectedLight;


    private Animation normalOffAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation normalOnAnimation;
    private Animation extinguishedAnimation;

    private List<EnergyConsumer> devices = new ArrayList<>();


    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.connectedLight = null;
        this.isOn = false;



        this.normalOffAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.normalOnAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.extinguishedAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalOffAnimation);
    }

    public void addDevice(EnergyConsumer device) {
        if (device != null) {
            devices.add(device);
        }
    }

    public void removeDevice(EnergyConsumer device) {
        if (device != null && devices.contains(device)) {
            devices.remove(device);
            device.setPowered(false);
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public int getDamage() {
        return this.damage;
    }


    @Override
    public void turnOn() {
        if (damage < 100) {
            isOn = true;
            updateDevices();
            updateAnimation();
        }
    }

    public boolean isOn() {
        return this.isOn;
    }

    @Override
    public void turnOff() {
        isOn = false;
        updateDevices();
        if (damage >= 100) {
            setAnimation(brokenAnimation);
        } else {
            setAnimation(normalOffAnimation);
        }
    }


    private void updateAnimation() {
        if (damage >= 100) {
            setAnimation(brokenAnimation);
        } else if (!isOn) {
            setAnimation(normalOffAnimation);
        } else if (temperature > 4000) {
            setAnimation(hotAnimation);
        } else {
            setAnimation(normalOnAnimation);
        }
    }

    public boolean extinguish() {
        if (damage < 100 || temperature <= 4000) {
            return false;
        }
        temperature = 4000;
        setAnimation(extinguishedAnimation);
        return true;
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !isOn || damage >= 100) return;

        int adjustedIncrement = calculateEffectiveIncrement(increment, damage);
        temperature += adjustedIncrement;

        if (temperature > 2000) {
            updateDamageBasedOnTemperature();
        }

        updateDevices();
        updateAnimation();
    }

    private int calculateEffectiveIncrement(int increment, int damage) {
        if (damage > 66) {
            return increment * 2;
        } else if (damage >= 33) {
            return (int) Math.ceil(increment * 1.5);
        } else {
            return increment;
        }
    }

    private void updateDamageBasedOnTemperature() {
        int calculatedDamage = (int) Math.floor((temperature - 2000) / 40.0);
        damage = Math.min(calculatedDamage, 100);
    }


    private void updateDevices() {
        for (EnergyConsumer device : devices) {
            device.setPowered(isOn && damage < 100);
        }
    }


    public void decreaseTemperature(int decrement) {
        if (!canDecreaseTemperature(decrement)) return;

        int effectiveDecrement = calculateEffectiveDecrement(decrement);

        applyTemperatureDecrease(effectiveDecrement);
        performUpdates();
    }

    private boolean canDecreaseTemperature(int decrement) {
        return isOn && decrement > 0 && damage < 100;
    }

    private int calculateEffectiveDecrement(int decrement) {
        return (damage >= 50) ? decrement / 2 : decrement;
    }

    private void applyTemperatureDecrease(int decrement) {
        temperature = Math.max(temperature - decrement, 0);
    }

    private void performUpdates() {
        updateDevices();
        updateAnimation();
    }


    public void repairWith(Hammer hammer) {
        if (hammer == null || damage == 0 || damage >= 100 || hammer.getRemainingUses() <= 0) {

            return;
        }

        hammer.use();

        int newDamage = Math.max(0, damage - 50);
        int newTemperature = 2000 + newDamage * 60;

        if (newTemperature < temperature){
            temperature = newTemperature;
        }
        damage = newDamage;


        updateAnimation();
        updateLightStatus();
    }


    @Override
    public boolean repair() {
        if (damage == 0) return false;
        damage = Math.max(0, damage - 50);

        updateTemperature();
        updateAnimation();

        return  true;
    }

    private void updateTemperature() {
        if (damage == 0) {
            temperature = 0;
        } else {
            temperature = 2000 + damage * 40;
        }
    }



    private void updateLightStatus() {
        if (connectedLight != null) {

            connectedLight.setElectricityFlow(isOn && damage < 100);
        }
    }

    public void extinguishWith(FireExtinguisher extinguisher) {
        if (extinguisher != null && damage < 100) {
            temperature = 4000;
            setAnimation(new Animation("sprites/reactor_extinguished.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
        }
    }

    public int getTemperature() {
        return this.temperature;
    }


}
