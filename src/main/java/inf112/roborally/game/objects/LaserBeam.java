package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;

public class LaserBeam extends MovableGameObject{
    private Board board;
    private ArrayList<Position> beam;

    public LaserBeam(int x, int y, Direction direction, Board board) {
        super(x, y, "");
        this.board = board;
        beam = new ArrayList<>();
        setDirection(direction);
        sprite = AssMan.manager.get(AssMan.FLAG_ATLAS).createSprite("1");
        sprite.setBounds(0,0,32,32);
    }

    @Override
    public void draw(SpriteBatch batch){
        update();
        for(Position pos : beam){
            sprite.setPosition(pos.getX()*32, pos.getY()*32);
            sprite.draw(batch);
        }
    }

    private void update() {
        beam.clear();
        if (beamBlockedByRobot()) return;
        MovableGameObject laserbeam = new MovableGameObject(getX(), getY(), "");
        laserbeam.setDirection(this.getDirection());
        while (laserbeam.canGo(laserbeam.getDirection(),board.getWallLayer())){
            if (laserbeam.crashWithRobot(laserbeam.getDirection(), board)) return;
            laserbeam.moveInDirection(getDirection());
            beam.add(new Position(laserbeam.getX(), laserbeam.getY()));
        }
    }

    private boolean beamBlockedByRobot() {
        for(Player robot : board.getPlayers()){
            if (position.equals(robot.position)) return true;
        }
        return false;
    }

}
