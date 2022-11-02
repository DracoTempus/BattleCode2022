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

        RobotInfo[] friendlyRobots = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam());
        RobotInfo[] enemies = rc.senseNearbyRobots(rt.actionRadiusSquared, rc.getTeam().opponent());

        if (enemies.length > 0) {
            System.out.println("WATCHTOWER FOUND SOMEONE");
            MapLocation toAttack = enemies[0].location;
            while (rc.canAttack(toAttack)) {
                rc.attack(toAttack);
                rc.setIndicatorString("ATTACKING " + enemies[0].ID);
                System.out.println("ATTACKING " + enemies[0].ID);
            }

            if (!rc.canSenseRobot(enemies[0].ID)) {
                System.out.println("Oh, Boy here I go killing again!");
                rc.setIndicatorString("Oh, Boy here I go killing again!");
                if (enemies[0].ID == rc.readSharedArray(ENEMY_1_BOTID.getIndex())) {
                    rc.writeSharedArray(ENEMY_1_BOTID.getIndex(), 0);
                    rc.writeSharedArray(ENEMY_1_X.getIndex(), 65535);
                    rc.writeSharedArray(ENEMY_1_Y.getIndex(), 65535);
                    rc.setIndicatorString("I will kill anyone, anywhere. Children, animals, old people, doesn't matter.");
                    System.out.println("I will kill anyone, anywhere. Children, animals, old people, doesn't matter.");
                }
            }
        }
    }
}
