package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private Teleport destination;
    private boolean isWork = true;

    public Teleport(Teleport destination) {
        this.destination = destination;
        Animation teleportAnimation = new Animation("sprites/lift.png");
        setAnimation(teleportAnimation);
    }

    public Teleport getDestination() {
        return destination;
    }

    public void setDestination(Teleport destinationTeleport) {
        if (this != destinationTeleport) {
            this.destination = destinationTeleport;
        }
    }

    public void teleportPlayer(Player player) {
        if (player != null) {
            player.setPosition(this.getPosX() + (this.getWidth() / 2) - (player.getWidth() / 2), this.getPosY() + (this.getHeight() / 2) - (player.getHeight() / 2));
            isWork = false;
        }
    }

    private boolean sniffsniff(Player player) {
        return player.getPosX() + player.getWidth() < this.getPosX()
            || player.getPosX() > this.getPosX() + this.getWidth()
            || player.getPosY() + player.getHeight() < this.getPosY()
            || player.getPosY() > this.getPosY() + this.getHeight();
    }

    public void teleporty() {
        Player player = getScene().getLastActorByType(Player.class);
        int pxCenter = player.getPosX() + player.getWidth() / 2;
        int pyCenter = player.getPosY() + player.getHeight() / 2;

        if (isWork && !sniffsniff(player) && destination != null
            && pxCenter >= this.getPosX() && pxCenter <= this.getPosX() + this.getWidth()
            && pyCenter >= this.getPosY() && pyCenter <= this.getPosY() + this.getHeight()) {
            destination.teleportPlayer(player);
        }
    }

    public void beOK() {
        Player player = getScene().getLastActorByType(Player.class);
        if (!isWork && sniffsniff(player)) {
            isWork = true;
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Player player = getScene().getLastActorByType(Player.class);

        new Loop<>(new Invoke<>(this::teleporty)).scheduleFor(player);
        new Loop<>(new Invoke<>(this::beOK)).scheduleFor(player);
    }
}
