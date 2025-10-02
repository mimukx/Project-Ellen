package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Ventilator extends AbstractActor implements Repairable{

    private boolean repaired;
    private Animation animation;

    public Ventilator(){
        animation = new Animation("sprites/ventilator.png", 32,32, 0.1f);
        animation.stop();
        setAnimation(animation);
        repaired = false;
    }

    @Override
    public boolean repair() {
        if ( !repaired ){
            repaired = true;
            animation.play();
            return true;
        }else return false;
    }

    @Override
    public boolean extinguish() {
        return false;
    }
}
