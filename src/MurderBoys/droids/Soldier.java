package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.*;

import java.util.Map;
import java.util.Objects;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;
import static battlecode.common.RobotType.*;


public class Soldier extends BaseDroid {

    public static void TakeTurn() throws GameActionException {
        RobotInfo[] friendlyRobots = rc.senseNearbyRobots(ARCHON.actionRadiusSquared, rc.getTeam());
        boolean nearHealer = false;
        for (RobotInfo friendly : friendlyRobots) {
            if (friendly.type == ARCHON) {
                nearHealer = true;
            }
        }
        if(rc.getHealth() < rt.getMaxHealth(rc.getLevel()) && nearHealer) {
            //WAIT HERE
        }
        else if(rc.getHealth() < rt.health/2.5) {
            RunToFightAnotherDay();
        }else {
            RobotInfo[] enemies = rc.senseNearbyRobots(rt.actionRadiusSquared, rc.getTeam().opponent());

            if (enemies.length > 0) {
                MapLocation toAttack = enemies[0].location;
                if (rc.canAttack(toAttack)) {
                    rc.attack(toAttack);
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
            lookForEnemies();

            Direction dir = directions[rng.nextInt(directions.length)];

            if (EnemyArchonsLocation.compareTo(noLocation) != 0) {
                dir = rc.getLocation().directionTo(EnemyArchonsLocation);
                rc.setIndicatorString("Going toward my target enemy Archon " + EnemyArchonsLocation);
                rc.setIndicatorLine(rc.getLocation(), EnemyArchonsLocation, 220, 220, 100);
            } else if (FirstEnemyLocation.compareTo(noLocation) != 0) {
                dir = rc.getLocation().directionTo(FirstEnemyLocation);
                rc.setIndicatorString("Going toward my target enemy " + FirstEnemyLocation);
                rc.setIndicatorLine(rc.getLocation(), EnemyArchonsLocation, 220, 220, 100);
            }

            if (rc.canMove(dir)) {
                while (rc.canMove(dir)) {
                    rc.move(dir);
                }
            } else {
                dir = directions[rng.nextInt(directions.length)];
                if (rc.canMove(dir)) {
                    rc.move(dir);
                    rc.setIndicatorLine(rc.getLocation(), new MapLocation(rc.getLocation().x + dir.dx, rc.getLocation().y + dir.dy), 220, 220, 100);
                }
            }
        }
    }

    public static void resetTarget(){

    }

    public static void lookForEnemies() throws GameActionException {
        if(rc.getLocation().compareTo(FirstEnemyLocation) < 3 && rc.canSenseRobot(ENEMY_1_BOTID.getIndex())){
            rc.writeSharedArray(ENEMY_1_BOTID.getIndex(), 0);
            rc.writeSharedArray(ENEMY_1_X.getIndex(), 65535);
            rc.writeSharedArray(ENEMY_1_Y.getIndex(), 65535);
        }

        if(rc.getLocation().compareTo(EnemyArchonsLocation) < 3 && rc.canSenseRobot(ENEMY_1_BOTID.getIndex())){
            rc.writeSharedArray(ENEMY_ARCHON_BOTID.getIndex(), 0);
            rc.writeSharedArray(ENEMY_ARCHON_X.getIndex(), 65535);
            rc.writeSharedArray(ENEMY_ARCHON_Y.getIndex(), 65535);
        }

        RobotInfo[] enemyDroids = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
            for (RobotInfo enemyDroid : enemyDroids) {
                if (enemyDroid.getType() == RobotType.ARCHON) {
                    System.out.println("FOUND ARCHON ");
                    rc.writeSharedArray(ENEMY_ARCHON_BOTID.getIndex(), enemyDroid.ID);
                    rc.writeSharedArray(ENEMY_ARCHON_X.getIndex(), enemyDroid.location.x);
                    rc.writeSharedArray(ENEMY_ARCHON_Y.getIndex(), enemyDroid.location.y);
                } else {
                    if (rc.readSharedArray(ENEMY_1_X.getIndex()) == 65535 && rc.readSharedArray(ENEMY_1_Y.getIndex()) == 65535) {
                        rc.writeSharedArray(ENEMY_1_BOTID.getIndex(), enemyDroid.ID);
                        rc.writeSharedArray(ENEMY_1_X.getIndex(), enemyDroid.location.x);
                        rc.writeSharedArray(ENEMY_1_Y.getIndex(), enemyDroid.location.y);
                        System.out.println("ENEMY_1 SET " + enemyDroid.location.x + " " + enemyDroid.location.y + " " + rc.readSharedArray(ENEMY_1_X.getIndex()));
                    }
                }
            }

        if(new MapLocation(rc.readSharedArray(ENEMY_ARCHON_X.getIndex()), rc.readSharedArray(ENEMY_ARCHON_Y.getIndex())).compareTo(EnemyArchonsLocation) != 0){
            EnemyArchonsLocation = new MapLocation(rc.readSharedArray(ENEMY_ARCHON_X.getIndex()), rc.readSharedArray(ENEMY_ARCHON_Y.getIndex()));
        }else if(new MapLocation(rc.readSharedArray(ENEMY_1_X.getIndex()), rc.readSharedArray(ENEMY_1_Y.getIndex())).compareTo(FirstEnemyLocation) != 0){
            FirstEnemyLocation = new MapLocation(rc.readSharedArray(ENEMY_1_X.getIndex()), rc.readSharedArray(ENEMY_1_Y.getIndex()));
        }
    }
}
