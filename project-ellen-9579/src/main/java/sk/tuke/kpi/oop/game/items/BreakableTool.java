package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.Actor;

public abstract class BreakableTool<T extends Actor> extends AbstractActor implements Usable<T> {
    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public void use() {
        if (remainingUses > 0) {
            remainingUses--;
            if (remainingUses == 0 && getScene() != null) {
                getScene().removeActor(this);
            }
        }
    }

    @Override
    public void useWith(T actor) {
        use();
    }

}
