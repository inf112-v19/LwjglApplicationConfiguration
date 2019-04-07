package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class LaserBeam extends MovableGameObject {
    private Board board;
    private ArrayList<Position> beam;

    public LaserBeam(int x, int y, Direction direction, Board board) {
        super(x, y, "");
        this.board = board;
        beam = new ArrayList<>();
        setDirection(direction);
        if (laserIsFlipped()) {
            setDirection(direction.getOppositeDirection());
        }
        sprite = new Sprite(new Texture("assets/objects/laser.png"));
        sprite.setBounds(0, 0, 32, 32);
        sprite.setOriginCenter();
        if (direction == Direction.WEST || direction == Direction.EAST) sprite.rotate(90);
    }

    private boolean laserIsFlipped() {
        return board.getLaserLayer().getCell(position.getX(), position.getY()).getFlipHorizontally()
                || board.getLaserLayer().getCell(position.getX(), position.getY()).getFlipVertically();
    }

    @Override
    public void draw(SpriteBatch batch) {
        beam.clear();
        update();
        for (Position pos : beam) {
            sprite.setPosition(pos.getX() * 32, pos.getY() * 32);
            sprite.draw(batch);
        }
    }

    private void update() {
        if (beamBlockedByRobot()) return;
        MovableGameObject laserbeam = new MovableGameObject(getX(), getY(), "");
        beam.add(new Position(laserbeam.getX(), laserbeam.getY()));
        laserbeam.setDirection(getDirection());
        while (true) {
            if (laserbeam.crashWithRobot(laserbeam.getDirection(), board)) return;
            if (!laserbeam.canGo(laserbeam.getDirection(), board.getWallLayer())) return;
            laserbeam.moveInDirection(getDirection());
            beam.add(new Position(laserbeam.getX(), laserbeam.getY()));
        }
    }

    private boolean beamBlockedByRobot() {
        for (Player robot : board.getPlayers()) {
            if (position.equals(robot.position)) return true;
        }
        return false;
    }

}
