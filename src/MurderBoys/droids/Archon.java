package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.*;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;
import static battlecode.common.RobotType.MINER;


public class Archon extends BaseDroid {

    public static void TakeTurn() throws GameActionException {

        RobotInfo[] friendlyRobots = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam());
        RobotInfo[] enemyRobots = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        Direction dir = directions[BUILDLOCATION];

        for (RobotInfo friendly:friendlyRobots) {
            if(friendly.health < friendly.type.health){
                if(rc.canRepair(friendly.getLocation())){
                    rc.repair(friendly.getLocation());
                }
            }
        }
        if(enemyRobots.length > 0 || rc.readSharedArray(ENEMY_ARCHON_BOTID.getIndex()) != 0 && SoldiersBuilt < 100){
            if (rc.canBuildRobot(RobotType.SOLDIER, dir)) {
                rc.buildRobot(RobotType.SOLDIER, dir);
                SoldiersBuilt++;
            }
            if (rc.canBuildRobot(RobotType.SOLDIER, dir)) {
                rc.buildRobot(RobotType.SOLDIER, dir);
                SoldiersBuilt++;
            }
        }

        if(rc.canBuildRobot(RobotType.SAGE, dir)){
            rc.buildRobot(RobotType.SAGE, dir);
            rc.writeSharedArray(SAGES_BUILT.getIndex(), rc.readSharedArray(SAGES_BUILT.getIndex()) + 1);
        }


        int minersBuilt = rc.readSharedArray(AMOUNT_OF_MINERS.getIndex());
        if (minersBuilt < MAX_MINERS) {
            // Let's try to build a miner.
            if (rc.canBuildRobot(MINER, dir)) {
                rc.writeSharedArray(AMOUNT_OF_MINERS.getIndex(), minersBuilt+1);
                rc.buildRobot(MINER, dir);
            }
        } else {
            if(rc.canBuildRobot(RobotType.BUILDER, dir) && SoldiersBuilt > MAX_SOLDIERS && BuildersBuilt < 4 ||rc.canBuildRobot(RobotType.BUILDER, dir) && BuildersBuilt < 1){
                BuildersBuilt++;
                rc.buildRobot(RobotType.BUILDER, dir);
                RobotInfo[] findLabBuilder = rc.senseNearbyRobots(1);
                for(RobotInfo LBuilder : findLabBuilder){
                    if(LBuilder.location.equals(new MapLocation(rc.getLocation().x + dir.dx, rc.getLocation().y + dir.dy))){
                        if(rc.readSharedArray(LABORATORY_BUILDER.getIndex()) == 0) {
                            rc.writeSharedArray(LABORATORY_BUILDER.getIndex(), LBuilder.ID);
                        }
                    }
                }
            } else if(SacrificesBuilt < 3){
                if(rc.canBuildRobot(RobotType.SOLDIER, dir)){
                    rc.buildRobot(RobotType.SOLDIER, dir);
                    SacrificesBuilt++;
                    RobotInfo[] findSoldier = rc.senseNearbyRobots(1);
                    for(RobotInfo soldier : findSoldier){
                        if(soldier.location.equals(new MapLocation(rc.getLocation().x + dir.dx, rc.getLocation().y + dir.dy))){
                            if(rc.readSharedArray(SACRIFICE_ID_1.getIndex()) == 0) {
                                rc.writeSharedArray(SACRIFICE_ID_1.getIndex(), soldier.ID);
                            }else if(rc.readSharedArray(SACRIFICE_ID_2.getIndex()) == 0) {
                                rc.writeSharedArray(SACRIFICE_ID_2.getIndex(), soldier.ID);
                            }else if(rc.readSharedArray(SACRIFICE_ID_3.getIndex()) == 0) {
                                rc.writeSharedArray(SACRIFICE_ID_3.getIndex(), soldier.ID);
                            }
                        }
                    }
                }
            }else if (rc.canBuildRobot(RobotType.SOLDIER, dir) && SacrificesBuilt >=3) {
                if(SoldiersBuilt < MAX_SOLDIERS || rc.getTeamLeadAmount(rc.getTeam()) > 300) {
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
