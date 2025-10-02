package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.Disposable;


public class DefectiveLight extends Light implements Repairable {
    private boolean isOn;

    private Disposable repairAction;

    public DefectiveLight() {


    }

    @Override
    public boolean repair() {
        if (!isOn()) {
            return false;
        }

        stopBlinking();
        repairAction = new Invoke<>(this::startBlinking).scheduleFor(this);

        return true;
    }

    @Override
    public boolean extinguish() {
        return false;
    }

    private void startBlinking() {
        repairAction = new Loop<>(new Invoke<>(this::blink)).scheduleFor(this);
    }

    private void stopBlinking() {
        if (repairAction != null) {
            repairAction.dispose();
        }
    }


    private void blink() {

        int randomValue = (int) (Math.random() * 20);


        if (randomValue == 1) {
            if (isOn) {
                turnOff();
            } else {
                turnOn();
            }
        }
    }


    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        startBlinking();
        new Loop<>(new Invoke<>(this::blink)).scheduleFor(this);
    }
}
