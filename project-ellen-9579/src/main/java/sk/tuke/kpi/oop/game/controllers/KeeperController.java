package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;


public class KeeperController implements KeyboardListener {

    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(Input.Key key) {
        inKeyEnter(key);
        inKeyB(key);
        inKeyU(key);
        inKeyS(key);
        inKeyBackspace(key);
    }

    public void inKeyEnter(Input.Key key){
        if (key == Input.Key.ENTER) {
//            new Take<Keeper<Collectible>>(Collectible.class).scheduleFor(actor);
            new Take<>().scheduleFor(keeper);
        }
    }
    public void inKeyB(Input.Key key){
        if (key == Input.Key.B && keeper.getBackpack().peek() != null && keeper.getBackpack().peek() instanceof Usable ) {
            Usable<?> usable = (Usable<?>) keeper.getBackpack().peek();
            new Use<>(usable).scheduleForIntersectingWith(keeper);
            keeper.getBackpack().remove(keeper.getBackpack().peek());
        }

    }
    public void inKeyU(Input.Key key){
        Usable<?> u = (Usable<?>) keeper.getScene().getActors().stream().filter(keeper::intersects).filter(Usable.class::isInstance).findFirst().orElse(null);

        if (key == Input.Key.U && keeper.getScene() != null && u != null) {
            new Use<>(u).scheduleForIntersectingWith(keeper);
        }
    }
    public  void inKeyBackspace(Input.Key key){
        if (key == Input.Key.BACKSPACE) {
//            new Drop<Keeper<Collectible>>().scheduleFor(actor);
            new Drop<>().scheduleFor(keeper);
        }
    }
    public void inKeyS(Input.Key key){
        if (key == Input.Key.S) {
            new Shift<>().scheduleFor(keeper);
        }
    }

}
