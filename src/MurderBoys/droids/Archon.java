package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.*;

import static MurderBoys.droids.base.BaseDroid.INDEX.AMOUNT_OF_MINERS;
import static battlecode.common.RobotType.MINER;


public class Archon extends BaseDroid {

    public static void TakeTurn() throws GameActionException {

        RobotInfo[] friendlyRobots = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam());
        RobotInfo[] enemyRobots = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());

        for (RobotInfo friendly:friendlyRobots) {
            if(friendly.health < friendly.type.health){
                if(rc.canRepair(friendly.getLocation())){
                    rc.repair(friendly.getLocation());
                }
            }
        }

        Direction dir = directions[BUILDLOCATION];
        int minersBuilt = rc.readSharedArray(AMOUNT_OF_MINERS.getIndex());
        if (minersBuilt < MAX_MINERS) {
            // Let's try to build a miner.
            if (rc.canBuildRobot(MINER, dir)) {
                rc.writeSharedArray(AMOUNT_OF_MINERS.getIndex(), minersBuilt+1);
                rc.buildRobot(MINER, dir);
            }
        } else {
            // Let's try to build a soldier.
            if(rc.canBuildRobot(RobotType.BUILDER, dir) && SoldiersBuilt > 10 && BuildersBuilt < 4){
                BuildersBuilt++;
                rc.buildRobot(RobotType.BUILDER, dir);
            } else if (rc.canBuildRobot(RobotType.SOLDIER, dir)) {
                if(SoldiersBuilt < 10 || rc.getTeamLeadAmount(rc.getTeam()) > 350) {
                    SoldiersBuilt++;
                    rc.buildRobot(RobotType.SOLDIER, dir);
                }
            }
        }
        BUILDLOCATION++;
        if(BUILDLOCATION > 7){
            BUILDLOCATION = 0;
        }

    }
}
