package inf112.skeleton.app;

import java.util.ArrayList;

public class ProgramCard {
    private int changeDirection;
    private int moveDistance;
    private int priority;

    public ProgramCard (int changeDirection, int moveDistance, int priority){
        this.changeDirection = changeDirection;
        this.moveDistance = moveDistance;
        this.priority = priority;
    }

    public int getMoveDistance(){ return this.moveDistance; }

    public int getChangeDirection() { return changeDirection; }

    public  int getPriority(){ return this.priority; }

    public static ArrayList<ProgramCard> makeStack(){
        ArrayList<ProgramCard> list = new ArrayList<ProgramCard>();

        // Adding cards that rotate:
        for(int i = 80; i <= 420; i+=20){
            // Left
            list.add(new ProgramCard(-1, 0, i));
            // Right
            list.add(new ProgramCard(1,0, i-10));
        }
        // 180
        for (int i = 10; i <= 60; i+=10) {
            list.add(new ProgramCard(2, 0, i));
        }

        // Adding cards that move:
        // Forward 1
        for(int i = 490; i <= 660; i+=10){
            list.add(new ProgramCard(0, 1, i));
        }
        // Forward 2
        for(int i = 670; i <= 780; i+=10){
            list.add(new ProgramCard(0, 2, i));
        }
        // Forward 3
        for(int i = 790; i <= 840; i+=10){
            list.add(new ProgramCard(0, 3, i));
        }
        // Backwards
        for(int i = 430; i <= 480; i+=10){
            list.add(new ProgramCard(0, -1, i));
        }
        return list;
    }

}
