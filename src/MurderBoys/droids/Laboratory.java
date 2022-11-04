package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;

import static MurderBoys.droids.base.BaseDroid.INDEX.LABORATORY_BUILDER;
import static battlecode.common.RobotType.*;


public class Laboratory extends BaseDroid {

    public static void TakeTurn() throws GameActionException {
        if (rc.canTransmute()) {
            rc.transmute();
        }
    }
}
