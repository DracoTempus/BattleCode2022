package ColdPocket.Bots;

import ColdPocket.Bots.Base.Droid;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;


public class Miner extends Droid {
    public static void TakeTurn() throws GameActionException{
        moveToLocation(lookForMinerals());
        tryMineAround();
        checkForEnemy();
        trySuicide();
        if(lookForMinerals() == null){
            rc.setIndicatorString("Can't find anything, moving randomly");
        }
    }
    private static void tryMineAround() throws GameActionException {
        for (Direction direction : directions) {
            MapLocation location = rc.adjacentLocation(direction);
            while (rc.canMineLead(location)){
                rc.mineLead(location);
            }
            while (rc.canMineGold(location)){
                rc.mineGold(location);
            }
        }
    }
}


