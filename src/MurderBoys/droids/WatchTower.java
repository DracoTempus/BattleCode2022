package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;
import static battlecode.common.RobotType.*;


public class WatchTower extends BaseDroid {

    public static void TakeTurn() throws GameActionException {
        TryAttack();
    }
}
