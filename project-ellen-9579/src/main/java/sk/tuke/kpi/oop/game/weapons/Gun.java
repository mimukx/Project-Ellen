package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm {

    public Gun(int initAmmo, int maxAmmo){
        super(initAmmo, maxAmmo);
    }

    public Gun(int initAmmo){
        super(initAmmo);
    }

    @Override
    public Fireable createBullet() {
        return new Bullet();
    }
}
