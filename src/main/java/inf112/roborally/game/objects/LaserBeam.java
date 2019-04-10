package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.Main;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.LaserAnimation;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class LaserBeam extends LaserAnimation {
    private Board board;
    private ArrayList<Position> beam;
    private Position position;
    private Direction direction;

    public LaserBeam(int x, int y, Direction direction, Board board) {
        this.board = board;
        this.direction = direction;
        position = new Position(x, y);
        beam = new ArrayList<>();
        sprite = new Sprite();
        sprite.setBounds(0, 0, Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
        sprite.setOriginCenter();
        sprite.setRotation(direction.getRotationDegree());

        if (laserIsFlipped()) {
            this.direction = direction.getOppositeDirection();
        }
    }

    public void fire() {
        MovableGameObject laser = new MovableGameObject(position.getX(), position.getY());
        laser.setDirection(this.direction);
        while (true) {
            for (Player robot : board.getPlayers()) {
                if (laser.position.equals(robot.position)) {
                    System.out.println("Lasers hit " + robot.getName());
                    robot.takeDamage();
                    if(!RoboRallyGame.soundMuted) {
                        playSound();
                    }
                    return;
                }
            }
            if (!laser.canGo(laser.getDirection(), board.getWallLayer())) return;
            laser.moveInDirection(laser.getDirection());
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.setRegion(getRegion());
        updateBeam();
        for (Position pos : beam) {
            sprite.setPosition(pos.getX() * 32, pos.getY() * 32);
            sprite.draw(batch);
        }
    }

    private void updateBeam() {
        beam.clear();
        if (beamBlockedByRobot()) return;

        MovableGameObject laserbeam = new MovableGameObject(position.getX(), position.getY());
        beam.add(new Position(laserbeam.getX(), laserbeam.getY()));
        laserbeam.setDirection(direction);
        while (true) {
            if (laserbeam.crashWithRobot(laserbeam.getDirection(), board)) return;

            if (!laserbeam.canGo(laserbeam.getDirection(), board.getWallLayer())) return;

            laserbeam.moveInDirection(direction);
            beam.add(laserbeam.position.copy());
        }
    }

    private boolean beamBlockedByRobot() {
        for (Player robot : board.getPlayers()) {
            if (position.equals(robot.position)) return true;
        }
        return false;
    }

    private boolean laserIsFlipped() {
        return board.getLaserLayer().getCell(position.getX(), position.getY()).getFlipHorizontally()
                || board.getLaserLayer().getCell(position.getX(), position.getY()).getFlipVertically();
    }
}
