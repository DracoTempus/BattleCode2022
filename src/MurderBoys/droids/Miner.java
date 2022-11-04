package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.*;

import static MurderBoys.droids.base.BaseDroid.INDEX.AMOUNT_OF_MINERS;


public class Miner extends BaseDroid {

    public static void TakeTurn() throws GameActionException {
        if(rt.health < 12){
            rc.writeSharedArray(AMOUNT_OF_MINERS.getIndex(), rc.readSharedArray(AMOUNT_OF_MINERS.getIndex()) -1);
            rc.disintegrate();
        }

        while(rc.getHealth() < rt.health/2){
            RunToFightAnotherDay();
        }

        if(!MineNearby()) {
            MoveToMine(rc);
        }
    }

    private static boolean MineNearby() throws GameActionException {
        boolean didIMine = false;
        MapLocation[] nearMines = findMinesByDistance(1);
        if(nearMines.length > 0){
            for (MapLocation mineLocation:nearMines) {
                    while (rc.canMineGold(mineLocation)) {
                        rc.mineGold(mineLocation);
                    }
                    while (rc.canMineLead(mineLocation)) {
                        rc.mineLead(mineLocation);
                    }
                }
        }
        return findMinesByDistance(1).length > 0;
    }

    private static void MoveToMine(RobotController rc) throws GameActionException {
        int resourceAmount = 0;
        MapLocation BestLocation = rc.getLocation();
        MapLocation[] mineFinder = new MapLocation[0];
        int divide = 3;

        while(mineFinder.length < 1){
            if(divide == 0)
                break;
            mineFinder = findMinesByDistance(rt.visionRadiusSquared/divide);
            divide--;
        }

        if(mineFinder.length > 0){
            for (MapLocation mineLocation : mineFinder) {
                if (rc.senseGold(mineLocation) > resourceAmount) {
                    resourceAmount = rc.senseGold(mineLocation);
                    BestLocation = mineLocation;
                }
                if (rc.senseLead(mineLocation) > resourceAmount) {
                    resourceAmount = rc.senseLead(mineLocation);
                    BestLocation = mineLocation;
                }
            }
        }
        Direction dir = directions[rng.nextInt(directions.length)];
        if(BestLocation == rc.getLocation()){
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
        }else {
            Direction toMine = rc.getLocation().directionTo(BestLocation);
            if (rc.canMove(toMine)) {
                rc.move(toMine);
            } else {

                if (rc.canMove(dir)) {
                    rc.move(dir);
                }
            }
        }

    }

}
