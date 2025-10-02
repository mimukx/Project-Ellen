package sk.tuke.kpi.oop.game;



import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class ChainBomb extends TimeBomb {

    public ChainBomb(float time) {
        super(time);
    }

    @Override
    protected void explode() {
        super.explode();

        Ellipse2D.Float explosionArea = new Ellipse2D.Float(this.getPosX() - 50, this.getPosY() - 50, 102, 102);

        getScene().getActors().stream()
            .filter(actor -> actor instanceof ChainBomb && actor != this)
            .map(actor -> (ChainBomb) actor)
            .forEach(bomb -> {
                Rectangle2D.Float bombArea = new Rectangle2D.Float(bomb.getPosX() - bomb.getWidth() / 2,
                    bomb.getPosY() - bomb.getHeight() / 2, bomb.getWidth(), bomb.getHeight());

                if (explosionArea.intersects(bombArea)) {
                    bomb.activate();
                }
            });
    }


}

