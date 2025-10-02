package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.SpawnPoint;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;


public class MissionImpossible implements SceneListener {

    private Ripley ripley;


    @Override
    public void sceneInitialized(Scene scene) {

        ripley = scene.getFirstActorByType(Ripley.class);


        scene.getGame().pushActorContainer(ripley.getBackpack());
        scene.follow(ripley);

        SpawnPoint spawnPoint = new SpawnPoint(5);
        scene.addActor(spawnPoint, 100, 100);



        MovableController movableController = new MovableController(ripley);
        Disposable movController = scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(ripley);
        Disposable colController = scene.getInput().registerListener(keeperController);

        Disposable energyDecrementSubscription  = scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Door) -> new Loop<>(new ActionSequence<>(new Invoke<Actor>(() -> ripley.getHealth().drain(50)), new Wait<>(1))).scheduleOn(scene));



        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> colController.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> new ActionSequence<>(new Invoke<Actor>(energyDecrementSubscription::dispose),
            new Invoke<Actor>(() -> scene.getGame().getOverlay().drawText("Ripley Died!", 100, 100).showFor(2)),
            new Wait<>(2)));


    }

    @Override
    public void sceneUpdating(Scene scene) {
        ripley.showRipleyState();
    }

    public static class Factory implements ActorFactory {

        @Nullable
        @Override
        public Actor create(String type, String name) {
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "door":
                    return new LockedDoor("door", Door.Orientation.VERTICAL);
                case "energy":
                    return new Energy();
                case "access card":
                    return new AccessCard();
                case "ventilator":
                    return new Ventilator();
                default:
                    return null;
            }
        }

    }

}
