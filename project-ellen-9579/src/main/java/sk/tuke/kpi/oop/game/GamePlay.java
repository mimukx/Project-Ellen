package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.items.Hammer;

import java.util.Map;


public class GamePlay extends Scenario {

    @Override
    public void setupPlay(Scene scene) {
        Reactor reactor = new Reactor();
        scene.addActor(reactor, 64, 64);
        reactor.turnOn();

        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, 100, 64);
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 150, 64);

        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repairWith(hammer))
        ).scheduleFor(reactor);


        DefectiveLight defectiveLight = new DefectiveLight();
        scene.addActor(defectiveLight, 200, 200);

        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker reactorArea1 = markers.get("reactor-area-1");

        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());

    }


}

