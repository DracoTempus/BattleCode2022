package ColdPocket.Bots;

import ColdPocket.Bots.Base.Droid;
import battlecode.common.*;


public class Soldier extends Droid {

    public static void TakeTurn() throws GameActionException {
        int index = 1;

        while (true) {
            checkForEnemy();
            lookForMinerals();
            attackAnything();
            if (rc.readSharedArray(index) != 65535 && rc.readSharedArray(index + 1) != 65535) {
                moveToLocation(new MapLocation(rc.readSharedArray(index), rc.readSharedArray(index + 1)));

                if(index == 1){
                    String TowerTarget="Archon";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget);
                }
                else if(index == 3){
                    String TowerTarget="Soldier";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget);
                }
                else if(index == 5){
                    String TowerTarget="Miner";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget);
                }
                else if(index == 7){
                    String TowerTarget="Sage";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget);
                }
                else if(index == 9){
                    String TowerTarget="Watch Tower";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget);
                }
                else if(index == 11){
                    String TowerTarget="Laboratory";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget);
                }
                break;
            }
            index = index + 2;
            if (index > 11) {
                moveRandomLocation();
                attackAnything();
                break;
            }
        }
    }
}


