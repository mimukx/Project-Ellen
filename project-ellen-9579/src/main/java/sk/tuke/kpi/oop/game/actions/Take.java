package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;


public class Take<K extends Keeper>extends AbstractAction<K>{
    public Take(){

    }

    @Override
    public void  execute(float deltaTime){
        if (getActor() != null && !isDone()) {
            helpFunction();
        }else {
            setDone(true);

        }
    }

    private void helpFunction(){
        List<Actor> items = getActor().getScene().getActors();
        for (Actor actors : items) {
            if(getActor().intersects(actors) && actors instanceof Collectible ){
                try {
                    getActor().getBackpack().add((Collectible) actors);
                    getActor().getScene().removeActor(actors);
                } catch (Exception e) {
                    getActor().getScene().getOverlay().drawText("Error",100,100).showFor(2);
                }
            }
        }
        setDone(true);
    }
}
