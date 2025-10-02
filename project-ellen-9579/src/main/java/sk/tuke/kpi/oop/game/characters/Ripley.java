package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;


public class Ripley extends AbstractActor implements Movable, Keeper, Armed, Alive{

    private Firearm firearm;
    private Health health;
    private int remainingAmmo;
    private Backpack backpack;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("RIPLEY_DIED", Ripley.class);
    private Disposable movController;
    private Disposable colController;
    private Disposable shController;

    public Ripley() {
        super("ellen");
        Animation animation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        firearm = new Gun(30);
        health = new Health(100);
        remainingAmmo = 50;
        animation.stop();
        backpack = new Backpack("Ripley's backpack", 10);
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public Backpack getBackpack(){return backpack;}

    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();

    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();
    }


    public int getRemainingAmmo() {
        return remainingAmmo;
    }

    public void setRemainingAmmo(int remainingAmmo) {
        this.remainingAmmo = remainingAmmo;
    }

    public void showRipleyState(){
        Scene scene = getScene();
        if(scene != null){
            int yTextPos = (scene.getGame().getWindowSetup().getHeight()) - GameApplication.STATUS_LINE_OFFSET;
            scene.getGame().getOverlay().drawText("| Energy: " + health.getValue(), 110, yTextPos);
            scene.getGame().getOverlay().drawText("   | Ammo: " + getFirearm().getAmmo(), 230, yTextPos);

            scene.getGame().pushActorContainer(getBackpack());
        }
    }



    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm firearm) {
        this.firearm = firearm;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    private void die(){

        getDieAnimation();
        shController.dispose();
        movController.dispose();
        colController.dispose();


        if(getScene() != null){
            getScene().getMessageBus().publish(RIPLEY_DIED, this);
            new ActionSequence<Actor>(
                new Wait<Actor>(2f),
                new Invoke<Actor>(() -> getScene().getGame().stop())
            ).scheduleOn(getScene());
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        getActorDie(scene);
        getKeeperController(scene);
        getShooterController(scene);

        new Loop<Actor>(
            new ActionSequence<Actor>(
                new Invoke<Actor>(this::alienIntersecting),
                new Wait<>(1f)
            )
        ).scheduleFor(this);
    }

    private void alienIntersecting(){
        if(getScene() != null){
            for (Actor actor : getScene().getActors()) {
                if (actor.intersects(this) && actor instanceof Enemy){
                    this.getHealth().drain(30);
                }

            }
        }
    }

    private void getDieAnimation(){
        Animation dyingAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f);
        dyingAnimation.setPlayMode(Animation.PlayMode.ONCE);
        setAnimation(dyingAnimation);
    }

    private void getShooterController(Scene scene){
        ShooterController shooterController = new ShooterController(this);
        this.shController = scene.getInput().registerListener(shooterController);
        super.addedToScene(scene);

    }

    private void getActorDie(Scene scene){
        this.getHealth().onFatigued(this::die);
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> scene.getGame().getOverlay().drawText("Ripley Died! ", 100, 100).showFor(2)) ;
        MovableController movableController = new MovableController(this);
        this.movController = scene.getInput().registerListener(movableController);

    }
    private void getKeeperController(Scene scene){
        KeeperController keeperController = new KeeperController(this);
        this.colController = scene.getInput().registerListener(keeperController);

    }
}
