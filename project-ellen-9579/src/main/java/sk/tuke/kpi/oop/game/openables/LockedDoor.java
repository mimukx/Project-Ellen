package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door {
    private boolean isLocked;

    public LockedDoor(String name, Orientation orientation){
        super(name,orientation);
        isLocked = true;
    }

    public boolean getLocked(){
        return isLocked;
    }

    public void doorOpen(){
        isLocked = false;
    }

    public void doorClose(){
        isLocked = true;
    }

    @Override
    public void useWith(Actor actor) {
        super.useWith(actor);
        if(getLocked()){
            doorClose();
        }else {
            doorOpen();
        }
    }

}
