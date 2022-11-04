package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

import static MurderBoys.droids.base.BaseDroid.INDEX.LABORATORY_BUILDER;
import static battlecode.common.RobotType.*;


public class Builder extends BaseDroid {



    public static void TakeTurn() throws GameActionException {

        RobotInfo[] friendlyRobots = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam());
        RobotInfo[] enemyRobots = rc.senseNearbyRobots(rc.getType().visionRadiusSquared, rc.getTeam().opponent());
        Direction ranDir = directions[rng.nextInt(directions.length-1)];

        if(rc.readSharedArray(LABORATORY_BUILDER.getIndex()) == rc.getID()) {
            if (rc.canBuildRobot(LABORATORY, MyArchon.directionTo(rc.getLocation()).rotateLeft()) && !LABORATORYBUILT) {
                LABORATORYBUILT = true;
                rc.buildRobot(LABORATORY, MyArchon.directionTo(rc.getLocation()).rotateLeft());
            } else if (rc.canBuildRobot(LABORATORY, ranDir) && !LABORATORYBUILT) {
                LABORATORYBUILT = true;
                rc.buildRobot(LABORATORY, ranDir);
            }
        }

        for (RobotInfo friendly:friendlyRobots) {
            if(friendly.health < friendly.type.health){
                while(rc.canRepair(friendly.getLocation())){
                    rc.repair(friendly.getLocation());
                }
            }
        }

        if(WATCHTOWERSBUILT > 0){
            WATCHTOWERSBUILT++;
            RobotInfo[] towerCheck = rc.senseNearbyRobots(1);
            for (RobotInfo friend:towerCheck) {
                if(friend.type == WATCHTOWER){
                    if(rc.canMutate(friend.location)){
                        rc.mutate(friend.location);
                    }
                    break;
                }
            }
        }


        rc.setIndicatorString("IM AM WITHIN " + rc.getLocation().compareTo(MyArchon) + " SPACES OF MY ARCHON");
        if(rc.getLocation().isWithinDistanceSquared(MyArchon, 2)){
            if(rc.canMove(ranDir)) {
                rc.move(ranDir);
            }
        }else{
            if(rc.canBuildRobot(WATCHTOWER, MyArchon.directionTo(rc.getLocation())) && WATCHTOWERSBUILT < 2){
                rc.buildRobot(WATCHTOWER, MyArchon.directionTo(rc.getLocation()));
                WATCHTOWERSBUILT++;
            }else if(rc.canBuildRobot(WATCHTOWER, MyArchon.directionTo(rc.getLocation())) && WATCHTOWERSBUILT < 2){
                rc.buildRobot(WATCHTOWER, MyArchon.directionTo(rc.getLocation()));
                WATCHTOWERSBUILT++;
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
