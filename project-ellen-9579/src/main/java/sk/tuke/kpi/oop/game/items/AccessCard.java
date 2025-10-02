package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor> {

    public AccessCard(){
        setAnimation(new Animation("sprites/key.png", 16,16));
    }


    @Override
    public void useWith(LockedDoor lockedDoor) {
        if (!lockedDoor.isOpen() ){
            lockedDoor.open();
        }else{
            lockedDoor.close();
        }
    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}
