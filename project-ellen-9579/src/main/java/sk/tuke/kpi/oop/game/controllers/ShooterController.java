package sk.tuke.kpi.oop.game.controllers;

import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

public class ShooterController implements KeyboardListener {

    private Armed armed;

    public ShooterController(Armed shooter){
        armed = shooter;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if (key == Input.Key.SPACE && armed.getFirearm() != null){
            newNewFire();
        }
    }
    private void newNewFire(){
        new Fire<>().scheduleFor(armed);
    }
}
