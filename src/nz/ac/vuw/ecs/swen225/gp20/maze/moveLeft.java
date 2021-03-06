package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * This class is created to move the player in the left direction
 */
public class moveLeft implements Move {

    @Override
    public void apply(Player player) {
        Tile nextTile = player.getGame().getMap()[player.getRow()][Math.max(player.getCol() - 1, 0)];
        if (nextTile.checkValidMove(player)) {
            player.setPosition(player.getRow(), Math.max(player.getCol() - 1, 0));
        }
    }

    @Override
    public String toString() {
        return "left";
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
