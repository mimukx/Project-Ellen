
package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.gamelib.Scene;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private boolean open;
    private Animation doorAnimation;
    private Orientation orientation;

    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    public enum Orientation {
        VERTICAL,
        HORIZONTAL
    }


    public Door(String name, Orientation orientation) {
        super(name);
        this.orientation = orientation;
        this.open = false;

        if (orientation == Orientation.VERTICAL) {
            this.doorAnimation = new Animation("sprites/vdoor.png", 16, 32, 0.1f);
        } else {
            this.doorAnimation = new Animation("sprites/hdoor.png", 32, 16, 0.1f);
        }

        this.doorAnimation.stop();
        setAnimation(doorAnimation);
        close();
        addWall();

    }

    @Override
    public void open() {
        if (!open) {
            open = true;
            doorAnimation.setPlayMode(Animation.PlayMode.ONCE);
            doorAnimation.play();
            deleteWall();
            if (getScene() != null) {
                getScene().getMessageBus().publish(DOOR_OPENED, this);
            }
        }
    }

    @Override
    public void close() {
        if (open) {
            open = false;
            doorAnimation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            doorAnimation.play();
            addWall();
            if (getScene() != null) {
                getScene().getMessageBus().publish(DOOR_CLOSED, this);
            }
        }
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    private void addWall() {
        if (!isOpen()) {
            Scene scene = getScene();
            if (scene == null) return;

            int xWall = getPosX() / 16;
            int yWall = getPosY() / 16;

            if (orientation == Orientation.VERTICAL) {
                MapTile tile = scene.getMap().getTile(xWall, yWall);
                MapTile tile1 = scene.getMap().getTile(xWall, yWall + 1);

                if (!tile.isWall()) tile.setType(MapTile.Type.WALL);
                if (!tile1.isWall()) tile1.setType(MapTile.Type.WALL);
            } else {
                MapTile tile = scene.getMap().getTile(xWall, yWall);
                MapTile tile1 = scene.getMap().getTile(xWall + 1, yWall);

                if (!tile.isWall()) tile.setType(MapTile.Type.WALL);
                if (!tile1.isWall()) tile1.setType(MapTile.Type.WALL);
            }
        }
    }

    private void deleteWall() {
        Scene scene = getScene();
        if (scene == null) return;

        int xWall = getPosX() / 16;
        int yWall = getPosY() / 16;

        if (orientation == Orientation.VERTICAL) {
            MapTile tile1 = scene.getMap().getTile(xWall, yWall);
            MapTile tile2 = scene.getMap().getTile(xWall, yWall + 1);

            if (tile1.isWall()) tile1.setType(MapTile.Type.CLEAR);
            if (tile2.isWall()) tile2.setType(MapTile.Type.CLEAR);
        } else {
            MapTile tile1 = scene.getMap().getTile(xWall, yWall);
            MapTile tile2 = scene.getMap().getTile(xWall + 1, yWall);

            if (tile1.isWall()) tile1.setType(MapTile.Type.CLEAR);
            if (tile2.isWall()) tile2.setType(MapTile.Type.CLEAR);
        }
    }

    @Override
    public void useWith(Actor actor) {
        if (isOpen()) {
            close();
        } else {
            open();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(!open){
            addWall();
        }
    }
}
