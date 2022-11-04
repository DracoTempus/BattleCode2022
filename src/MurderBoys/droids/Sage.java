package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;


public class Sage extends BaseDroid {

    public static void TakeTurn() throws GameActionException {
        TryAttack();
        rc.setIndicatorString(String.valueOf(rc.readSharedArray(ENEMY_ARCHON_X.getIndex())));
        if(rc.readSharedArray(SAGES_BUILT.getIndex()) > 2){
            if(rc.readSharedArray(ENEMY_ARCHON_X.getIndex()) < 31337){
                MapLocation enemyArchon = new MapLocation(rc.readSharedArray(ENEMY_ARCHON_X.getIndex()), rc.readSharedArray(ENEMY_ARCHON_Y.getIndex()));

                if(rc.canMove(rc.getLocation().directionTo(enemyArchon))){
                    rc.move(rc.getLocation().directionTo(enemyArchon));
                }
            }
        }
    }
}
