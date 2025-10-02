package sk.tuke.kpi.oop.game;


public enum Direction {
    NORTH(0, 1),
    NORTHWEST(-1, 1),
    NORTHEAST(1,1),
    WEST(-1, 0),
    EAST(1, 0),
    SOUTHWEST(-1, -1),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
    NONE(0, 0);

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    private final int dx, dy;


    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public float getAngle(){
        switch (this){
            case NORTHWEST: return 45;
            case WEST: return 90;
            case SOUTHWEST: return 135;
            case SOUTH: return 180;
            case SOUTHEAST: return 225;
            case EAST: return 270;
            case NORTHEAST: return 315;
            default:
                return 0;
        }
    }

    public Direction combine(Direction other){
        int x;
        int y;
        if (this == Direction.NONE  ){
            return other;
        }
        if (other == null || other == Direction.NONE){
            return this;
        }


        x = other.getDx() + this.getDx();
        y = this.getDy() + other.getDy();

        int hx = update(x);
        int hy = update(y);
        Direction help = Direction.NONE;
        for (Direction dir : Direction.values()) {
            if (dir.getDx() == hx && dir.getDy() == hy ){
                help = dir;
            }
        }
        return help;
    }
    public static Direction fromAngle(float angle){
        for (Direction help : Direction.values()) {
            if (help.getAngle() == angle) {
                return help;
            }
        }
        return Direction.NONE;
    }

    private int update(int arg){
        int numb;
        if (arg >= 1) {
            numb = 1;
        } else if (arg <= -1){
            numb = -1;
        } else{
            numb= 0;
        }
        return numb;
    }
}
