package MurderBoys.droids;

import MurderBoys.droids.base.BaseDroid;
import battlecode.common.*;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;
import static battlecode.common.RobotType.*;


public class Soldier extends BaseDroid {

    public static void TakeTurn() throws GameActionException {

        if(rc.readSharedArray(SACRIFICE_ID_1.getIndex()) == rc.getID() ||
                rc.readSharedArray(SACRIFICE_ID_2.getIndex()) == rc.getID() ||
                rc.readSharedArray(SACRIFICE_ID_3.getIndex()) == rc.getID()) {

            SUPERSMARTYPANTS = true;
        }

        if(SUPERSMARTYPANTS){
            if(SACDirection == null) {
                if (rc.readSharedArray(SACRIFICE_ID_1.getIndex()) == rc.getID()) {
                    MapLocation CallLight = BringInLight();
                    if (CallLight.x == 0) {
                        SACDirection = Direction.WEST;
                    } else {
                        SACDirection = Direction.EAST;
                    }
                }
                if (rc.readSharedArray(SACRIFICE_ID_2.getIndex()) == rc.getID()) {
                    MapLocation CallLight = BringInLight();
                    if (CallLight.y == 0) {
                        SACDirection = Direction.SOUTH;
                    } else {
                        SACDirection = Direction.NORTH;
                    }
                }
                if (rc.readSharedArray(SACRIFICE_ID_3.getIndex()) == rc.getID()) {
                    MapLocation CallLight = BringInLight();
                    int YDirection = 0;
                    int XDirection = 0;
                    if (CallLight.y == 0 && CallLight.x == 0) {
                        SACDirection = Direction.SOUTHWEST;
                    } else if (CallLight.y > 0 && CallLight.x > 0){
                        SACDirection = Direction.NORTHEAST;
                    } else if(CallLight.y == 0 && CallLight.x > 0) {
                        SACDirection = Direction.NORTHWEST;
                    }else{
                        SACDirection = Direction.SOUTHEAST;
                    }
                }
            }
            rc.setIndicatorString("IM GOING FOREVER" + SACDirection);
            if (rc.canMove(SACDirection)) {
                rc.move(SACDirection);
            }
        }

        if(!SUPERSMARTYPANTS) {
            RobotInfo[] friendlyRobots = rc.senseNearbyRobots(ARCHON.actionRadiusSquared, rc.getTeam());
            boolean nearHealer = false;
            for (RobotInfo friendly : friendlyRobots) {
                if (friendly.type == ARCHON) {
                    nearHealer = true;
                }
            }
            if (rc.getHealth() < rt.getMaxHealth(rc.getLevel()) && nearHealer) {
                //WAIT HERE
            } else if (rc.getHealth() < rt.health / 2.5) {
                RunToFightAnotherDay();
            } else {
                RobotInfo[] enemies = rc.senseNearbyRobots(rt.actionRadiusSquared, rc.getTeam().opponent());

                if (enemies.length > 0) {
                    RobotInfo enemyToAttack = enemies[0];
                    for (RobotInfo enemy : enemies) {
                        if (enemy.type == SOLDIER || enemy.type == WATCHTOWER || enemy.type == SAGE) {
                            enemyToAttack = enemy;
                            break;
                        }
                    }
                    if (rc.canAttack(enemyToAttack.location)) {
                        rc.attack(enemyToAttack.location);
                    }

                    if (!rc.canSenseRobot(enemyToAttack.ID)) {
                        rc.setIndicatorString("Oh, Boy here I go killing again!");
                        if (enemyToAttack.ID == rc.readSharedArray(ENEMY_1_BOTID.getIndex())) {
                            if (enemyToAttack.type != ARCHON) {
                                rc.writeSharedArray(ENEMY_ARCHON_BOTID.getIndex(), 0);
                                rc.writeSharedArray(ENEMY_ARCHON_X.getIndex(), 65535);
                                rc.writeSharedArray(ENEMY_ARCHON_Y.getIndex(), 65535);
                                rc.setIndicatorString("I will kill anyone, anywhere. Children, animals, old people, doesn't matter.");
                                System.out.println("I will kill anyone, anywhere. Children, animals, old people, doesn't matter.");
                            } else {
                                rc.writeSharedArray(ENEMY_1_BOTID.getIndex(), 0);
                                rc.writeSharedArray(ENEMY_1_X.getIndex(), 65535);
                                rc.writeSharedArray(ENEMY_1_Y.getIndex(), 65535);
                                rc.setIndicatorString("I will kill anyone, anywhere. Children, animals, old people, doesn't matter.");
                                System.out.println("I will kill anyone, anywhere. Children, animals, old people, doesn't matter.");
                            }
                        }
                    }
                }
                lookForEnemies();

                Direction dir = directions[rng.nextInt(directions.length)];

                if (!EnemyArchonsLocation.isWithinDistanceSquared(rc.getLocation(), rt.actionRadiusSquared)) {
                    dir = rc.getLocation().directionTo(EnemyArchonsLocation);
                    rc.setIndicatorString("Going toward my target enemy Archon " + EnemyArchonsLocation);
                    rc.setIndicatorLine(rc.getLocation(), EnemyArchonsLocation, 220, 220, 100);
                } else if (!FirstEnemyLocation.isWithinDistanceSquared(rc.getLocation(), rt.actionRadiusSquared)) {
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
    }

    public static void resetTarget(){

    }

    public static void lookForEnemies() throws GameActionException {
        if(rc.getLocation().isWithinDistanceSquared(FirstEnemyLocation, rt.actionRadiusSquared) && !rc.canSenseRobot(ENEMY_1_BOTID.getIndex())){
            rc.writeSharedArray(ENEMY_1_BOTID.getIndex(), 0);
            rc.writeSharedArray(ENEMY_1_X.getIndex(), 65535);
            rc.writeSharedArray(ENEMY_1_Y.getIndex(), 65535);
        }

        if(rc.getLocation().isWithinDistanceSquared(EnemyArchonsLocation, rt.actionRadiusSquared) && !rc.canSenseRobot(ENEMY_ARCHON_BOTID.getIndex())){
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
