package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;


public class Ammo extends AbstractActor implements Usable<Armed> {
    private int maxAmmo = 500;

    public Ammo() {
        Animation animation = new Animation("sprites/ammo.png", 16, 16);
        setAnimation(animation);
    }

    @Override
    public void useWith(Armed ripley) {
        if (ripley != null && ripley.getFirearm().getAmmo() < maxAmmo) {
            ripley.getFirearm().reload(10);
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
}
