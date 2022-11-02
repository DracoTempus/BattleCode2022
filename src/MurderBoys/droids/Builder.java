package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

import static battlecode.common.RobotType.*;


public class Builder extends BaseDroid {



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

        Direction ranDir = directions[rng.nextInt(directions.length-1)];
        rc.setIndicatorString("IM AM WITHIN " + rc.getLocation().compareTo(MyArchon) + " SPACES OF MY ARCHON");
        if(rc.getLocation().isWithinDistanceSquared(MyArchon, 2)){
            if(rc.canMove(ranDir)) {
                rc.move(ranDir);
            }
        }else{
            if(rc.canBuildRobot(WATCHTOWER, MyArchon.directionTo(rc.getLocation())) && !WATCHTOWERBUILT){
                rc.buildRobot(WATCHTOWER, MyArchon.directionTo(rc.getLocation()));
                WATCHTOWERBUILT = true;
            }else if(rc.canBuildRobot(WATCHTOWER, ranDir) && !WATCHTOWERBUILT){
                rc.buildRobot(WATCHTOWER, MyArchon.directionTo(rc.getLocation()));
                WATCHTOWERBUILT = true;
            }
            if(rc.canBuildRobot(LABORATORY, MyArchon.directionTo(rc.getLocation()).rotateLeft()) && !LABORATORYBUILT){
                rc.buildRobot(LABORATORY, MyArchon.directionTo(rc.getLocation()).rotateLeft());
                LABORATORYBUILT = true;
            }else if (rc.canBuildRobot(LABORATORY, ranDir) && !LABORATORYBUILT){
                rc.buildRobot(LABORATORY, ranDir);
                LABORATORYBUILT = true;
            }
        }
        if(rc.canBuildRobot(ARCHON, MyArchon.directionTo(rc.getLocation()).rotateRight()) && !ARCHONBUILT){
            rc.buildRobot(ARCHON, MyArchon.directionTo(rc.getLocation()).rotateRight());
            ARCHONBUILT = true;
        }else if(rc.canBuildRobot(ARCHON, ranDir) && !ARCHONBUILT){
            rc.buildRobot(ARCHON, ranDir);
            ARCHONBUILT = true;
        }

    }
}
