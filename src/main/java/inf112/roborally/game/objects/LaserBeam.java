package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.Main;
import inf112.roborally.game.animations.LaserAnimation;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.player.Player;

import java.util.ArrayList;

public class LaserBeam extends MovableGameObject {
    private Board board;
    private ArrayList<Position> beam;
    private LaserAnimation animation;

    public LaserBeam(int x, int y, Direction direction, Board board) {
        super(x, y, "");
        this.board = board;
        animation = new LaserAnimation();
        beam = new ArrayList<>();
        setDirection(direction);
        if (laserIsFlipped()) {
            setDirection(direction.getOppositeDirection());
        }
        sprite = new Sprite();
        sprite.setBounds(0, 0, Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
        sprite.setOriginCenter();
        sprite.setRotation(getDirection().getRotationDegree());
    }

    public void fire() {
        MovableGameObject laser = new MovableGameObject(getX(), getY(), "");
        laser.setDirection(this.getDirection());
        while (laser.canGo(laser.getDirection(), board.getWallLayer())) {
            for (Player robot : board.getPlayers()) {
                if (laser.position.equals(robot.position)) {
                    System.out.println("Lasers hit " + robot.getName());
                    robot.takeDamage();
                    animation.playSound();
                    return;
                }
            }
            laser.moveInDirection(laser.getDirection());
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setRegion(animation.getRegion());
        updateBeam();
        for (Position pos : beam) {
            sprite.setPosition(pos.getX() * 32, pos.getY() * 32);
            sprite.draw(batch);
        }
    }

    private void updateBeam() {
        beam.clear();
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

    private boolean laserIsFlipped() {
        return board.getLaserLayer().getCell(position.getX(), position.getY()).getFlipHorizontally()
                || board.getLaserLayer().getCell(position.getX(), position.getY()).getFlipVertically();
    }
}
