package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {


    private int initAmmo;
    private int maxAmmo;

    public Firearm(int initAmmo, int maxAmmo){
        this.initAmmo = initAmmo;
        this.maxAmmo = maxAmmo;
    }
    public Firearm(int initAmmo){
        this(initAmmo, initAmmo);
    }

    public int getAmmo() {
        return initAmmo;
    }

    public void reload(int newAmmo){
        if (getAmmo() + newAmmo > maxAmmo) initAmmo = maxAmmo;
        else initAmmo += newAmmo;
    }

    public Fireable fire(){
        if (initAmmo == 0){
            return null;
        }else{
            initAmmo--;
            return createBullet();
        }
    }

    public abstract Fireable createBullet();


}
