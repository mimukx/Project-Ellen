package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;


public class Energy extends AbstractActor implements Usable<Alive>{

    public Energy(){
        Animation animation = new Animation("sprites/energy.png", 16, 16);
        setAnimation(animation);
    }

    @Override
    public void useWith(Alive actor) {
        if (actor != null && actor.getHealth().getValue() != actor.getHealth().getMaxHealth()) {
            actor.getHealth().restore();
            getScene().removeActor(this);
            getCount();
        }
    }

    public int getCount() {
        int number = 0;

        if(number == 0){
            number = 2;
        }else{
            number = 5;
        }
        return 0;
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }


}
