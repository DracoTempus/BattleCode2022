package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;


public class Sage extends BaseDroid {

    public static void TakeTurn() throws GameActionException {
        TryAttack();

        if(rc.readSharedArray(ENEMY_ARCHON_X.getIndex()) < 31337){
            MapLocation enemyArchon = new MapLocation(rc.readSharedArray(ENEMY_ARCHON_X.getIndex()), rc.readSharedArray(ENEMY_ARCHON_Y.getIndex()));
            if(rc.canMove(rc.getLocation().directionTo(enemyArchon))){
                rc.move(rc.getLocation().directionTo(enemyArchon));
            }
        }else{
            MapLocation CallLight = BringInLight();
            if (CallLight.y == 0 && CallLight.x == 0) {
                SACDirection = Direction.SOUTHWEST;
            } else if (CallLight.y > 0 && CallLight.x > 0){
                SACDirection = Direction.NORTHEAST;
            } else if(CallLight.y == 0 && CallLight.x > 0) {
                SACDirection = Direction.SOUTHEAST;
            }else {
                SACDirection = Direction.NORTHWEST;
            }
            rc.setIndicatorString(CallLight.y + " " + CallLight.x + " " + " GOING " +SACDirection);
            LetsMove(SACDirection);
        }
    }
}
