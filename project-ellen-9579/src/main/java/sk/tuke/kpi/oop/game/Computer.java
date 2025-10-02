package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private Animation computerAnimation;
    private boolean isPowered;

    public Computer() {

        this.computerAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(computerAnimation);
    }

    @Override
    public void setPowered(boolean power) {
        this.isPowered = power;
        if (power) {
            computerAnimation.play();
        } else {
            computerAnimation.stop();
        }
    }



    public int add(int a, int b) {
        return isPowered ? a + b : 0;
    }

    public float add(float a, float b) {
        return isPowered ? a + b : 0;
    }

    public int sub(int a, int b) {
        return isPowered ? a - b : 0;
    }

    public float sub(float a, float b) {
        return isPowered ? a - b : 0;
    }


}


