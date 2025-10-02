package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<A extends Armed> extends AbstractAction<A> {

    private boolean firstTime;

    public Fire(){
        firstTime = true;
    }

    @Override
    public void execute(float deltaTime) {
        if (isDone() || getActor() == null){
            setDone(true);
            return;
        }

        if (firstTime) {
            Fireable fireable = getActor().getFirearm().fire();
            if (fireable == null) {
                setDone(true);
                return;
            }
            firstTime = false;
            if(getActor().getScene() != null){
                fireable.getAnimation().setRotation(Direction.fromAngle(getActor().getAnimation().getRotation()).getAngle());
                getActor().getScene().addActor(fireable, getActor().getPosX() + 8, getActor().getPosY() + 8);
                fireBoleMove(fireable);
            }
        }
        setDone(true);
    }

    private void fireBoleMove(Fireable fireable){
        fireable.getAnimation().setRotation(Direction.fromAngle(getActor().getAnimation().getRotation()).getAngle());
        new Move<Fireable>(Direction.fromAngle(getActor().getAnimation().getRotation()), 9999999999f).scheduleFor(fireable);
    }
}
