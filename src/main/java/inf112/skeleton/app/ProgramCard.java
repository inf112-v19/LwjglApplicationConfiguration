package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ProgramCard {
    private int changeDirection;
    private int moveDistance;
    private int priority;

    private ProgramCard (int changeDirection, int moveDistance, int priority){
        this.changeDirection = changeDirection;
        this.moveDistance = moveDistance;
        this.priority = priority;
    }

    public int getMoveDistance(){ return this.moveDistance; }

    public int getChangeDirection() { return this.changeDirection; }

    public  int getPriority(){ return this.priority; }

    public static ArrayList<ProgramCard> makeStack(){
        ArrayList<ProgramCard> list = new ArrayList<>();

        // Adding cards that rotate:
        for (int priority = 10; priority <= 60; priority+=10) {
            // 180
            list.add(new ProgramCard(2, 0, priority));
        }
        for(int priority = 80; priority <= 420; priority+=20){
            // Right
            list.add(new ProgramCard(1,0, priority-10));
            // Left
            list.add(new ProgramCard(-1, 0, priority));
        }

        // Adding cards that move:
        // Backwards
        for(int priority = 430; priority <= 480; priority+=10){
            list.add(new ProgramCard(0, -1, priority));
        }
        // Forwards 1
        for(int priority = 490; priority <= 660; priority+=10){
            list.add(new ProgramCard(0, 1, priority));
        }
        // Forwards 2
        for(int priority = 670; priority <= 780; priority+=10){
            list.add(new ProgramCard(0, 2, priority));
        }
        // Forwards 3
        for(int priority = 790; priority <= 840; priority+=10){
            list.add(new ProgramCard(0, 3, priority));
        }
//        Collections.shuffle(list);
        return list;
    }

    @Override
    public String toString(){
        String s = "";
        if(this.moveDistance == 0){
            switch (changeDirection){
                case -1 :
                    s = "Turn left,";
                    break;
                case 1:
                    s = "Turn right,";
                    break;
                case 2:
                    s = "U turn,";
                    break;
            }
        }
        else
            s = "Move " + moveDistance + ",";

        return s + " priority: " + priority;
    }


}
