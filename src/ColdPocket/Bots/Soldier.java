package ColdPocket.Bots;

import ColdPocket.Bots.Base.Droid;
import battlecode.common.*;


public class Soldier extends Droid {

    public static void TakeTurn() throws GameActionException, InterruptedException {
        if(PlaySafe() == 0){
            if(Attack()){
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


