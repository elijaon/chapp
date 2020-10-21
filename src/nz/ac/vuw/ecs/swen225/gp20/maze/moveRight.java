package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the right direction
 */
public class moveRight extends Move {

    /**
     * Move right constructor.
     * @param actor -- actor that perform this action.
     */
    public moveRight(Actor actor) {
        super(actor);
        dir = 1;
    }

    @Override
    public void apply() {
        Tile nextTile = mover.getGame().getMap()[mover.getRow()][Math.min(mover.getCol() + 1, 24)];
        if(!(mover instanceof Player) || nextTile.checkValidMove((Player) mover)){
            mover.setPosition(mover.getRow(), Math.min(mover.getCol() + 1, 24));
        }
    }

    @Override
    public String toString() {
        return "right";
    }
}