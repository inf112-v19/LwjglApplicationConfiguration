package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;


public class ProgramCard implements Comparable {
    private Rotate rotate;
    private int moveDistance;
    private int priority;

    public ProgramCard(){
    }

    private ProgramCard(Rotate rotate, int moveDistance, int priority){
        this.rotate = rotate;
        this.moveDistance = moveDistance;
        this.priority = priority;
    }

    public int getMoveDistance(){ return this.moveDistance; }

    public Rotate getRotate() { return this.rotate; }

    public int getPriority(){ return this.priority; }

    public static ArrayList<ProgramCard> makeStack(){
        ArrayList<ProgramCard> list = new ArrayList<>();

        // Adding cards that rotate:
        for (int priority = 10; priority <= 60; priority+=10) {
            // 180
            list.add(new ProgramCard(Rotate.UTURN, 0, priority));
        }
        for(int priority = 80; priority <= 420; priority+=20){
            // Right
            list.add(new ProgramCard(Rotate.RIGHT,0, priority-10));
            // Left
            list.add(new ProgramCard(Rotate.LEFT, 0, priority));
        }

        // Adding cards that move:
        // Backwards
        for(int priority = 430; priority <= 480; priority+=10){
            list.add(new ProgramCard(Rotate.NONE, -1, priority));
        }
        // Forwards 1
        for(int priority = 490; priority <= 660; priority+=10){
            list.add(new ProgramCard(Rotate.NONE, 1, priority));
        }
        // Forwards 2
        for(int priority = 670; priority <= 780; priority+=10){
            list.add(new ProgramCard(Rotate.NONE, 2, priority));
        }
        // Forwards 3
        for(int priority = 790; priority <= 840; priority+=10){
            list.add(new ProgramCard(Rotate.NONE, 3, priority));
        }
        Collections.shuffle(list);
        return list;
    }


    @Override
    public String toString(){
        String s = "";
        if(this.moveDistance == 0){
            switch (rotate){
                case LEFT :
                    s = "Turn left,";
                    break;
                case RIGHT:
                    s = "Turn right,";
                    break;
                case UTURN:
                    s = "U turn,";
                    break;
            }
        }
        else
            s = "Move " + moveDistance + ",";

        return s + " priority: " + priority;
    }

    @Override
    public int compareTo(Object o){
        if(this.getClass() != o.getClass())
            return 0;
        ProgramCard other = (ProgramCard) o;
        if(this.getPriority() > other.getPriority())
            return -1;
        return  1;

    }

}
