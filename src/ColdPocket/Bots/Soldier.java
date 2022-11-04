package ColdPocket.Bots;

import ColdPocket.Bots.Base.Droid;
import battlecode.common.*;


public class Soldier extends Droid {

    public static void TakeTurn() throws GameActionException, InterruptedException {
        if(PlaySafe() == 0){
            if(rc.getHealth() < rc.getType().getMaxHealth(rc.getLevel()) - 25){
                getHealsFromArchon();
                rc.setIndicatorString("Im going to get heals from the archon at | x: " + rc.readSharedArray(19) + " | y: " + rc.readSharedArray(20));
            }
            if(canAttack()){
                moveRandomLocation();
                attackAnything();
            }
            checkForEnemy();
            lookForMinerals();
            attackAnything();
        }
        else if(PlaySafe() == 1){
            rc.setIndicatorString("Playing Safe");

        }
    }
}


