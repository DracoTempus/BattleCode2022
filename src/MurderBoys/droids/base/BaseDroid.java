package MurderBoys.droids.base;

import battlecode.common.*;

import java.util.Random;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;
import static battlecode.common.RobotType.*;
import static battlecode.common.RobotType.ARCHON;

public class BaseDroid {

    public static RobotController rc;
    public static RobotType rt;
    public static MapLocation noLocation = new MapLocation(65535,65535);
    public static MapLocation EnemyArchonsLocation = noLocation;
    public static MapLocation FirstEnemyLocation = noLocation;
    public static MapLocation MyArchon = noLocation;

    public static RobotType[] DangerousTypes = {
           RobotType.SOLDIER,
           RobotType.SAGE,
           RobotType.WATCHTOWER
    };

    //BUILDER SPECIFIC//
    public static boolean SUPERSMARTYPANTS = false;
    public static Direction SACDirection = null;

    //BUILDER SPECIFIC//
    public static int WATCHTOWERSBUILT = 0;
    public static boolean LABORATORYBUILT = false;
    public static boolean ARCHONBUILT = false;

    ///ARCHON SPECIFIC//
    public static int BUILDLOCATION = 0;
    public static int MAX_MINERS = 10;
    public static int MAX_SOLDIERS = 1;
    public static int SoldiersBuilt = 0;
    public static int BuildersBuilt = 0;
    public static int SacrificesBuilt = 0;

    public enum INDEX{
        ENEMY_ARCHON_BOTID  (1),
        ENEMY_ARCHON_X      (2),
        ENEMY_ARCHON_Y      (3),
        ENEMY_SOLDIER_BOTID (4),
        ENEMY_SOLDIERN_X    (5),
        ENEMY_SOLDIER_Y     (6),
        ENEMY_1_BOTID       (7),
        ENEMY_1_X           (8),
        ENEMY_1_Y           (9),
        ENEMY_2_BOTID       (10),
        ENEMY_2_X           (11),
        ENEMY_2_Y           (12),
        SAGES_BUILT         (51),
        LABORATORY_BUILDER  (52),
        MINE_1_X            (53),
        MINE_1_Y            (54),
        MINE_2_X            (55),
        MINE_2_Y            (56),
        SACRIFICE_ID_1      (57),
        SACRIFICE_ID_2      (58),
        SACRIFICE_ID_3      (59),
        SACRIFICE_ID_4      (60),
        SACRIFICE_ID_5      (61),
        SACRIFICE_ID_6      (62),
        AMOUNT_OF_MINERS    (63);

        INDEX(int index){
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        final int index;
    }

    public static void initialize(RobotController robotController) throws GameActionException {
        rc = robotController;
        rt = robotController.getType();
        if(rt != RobotType.ARCHON){
            for(RobotInfo whereIsMyCreator : rc.senseNearbyRobots(2)){
               if(whereIsMyCreator.type == RobotType.ARCHON){
                   MyArchon =whereIsMyCreator.getLocation();
                   break;
               }
            }
        }
        if(rt == RobotType.ARCHON){
            MAX_MINERS = (rc.getMapHeight() * rc.getMapWidth()) / 150;
            rc.writeSharedArray(ENEMY_ARCHON_X.getIndex(), 65535);
            rc.writeSharedArray(ENEMY_ARCHON_Y.getIndex(), 65535);
            rc.writeSharedArray(ENEMY_1_X.getIndex(), 65535);
            rc.writeSharedArray(ENEMY_1_Y.getIndex(), 65535);
        }
    }

    static final public Random rng = new Random(6147);

    static final public Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    public static void GoTowardsLocation(RobotController rc, MapLocation Target){

    }

    public static MapLocation BringInLight(){
        int MapSizeX = rc.getMapWidth();
        int MapSizeY = rc.getMapHeight();
        int MyArchonX = MyArchon.x;
        int MyArchonY = MyArchon.y;

        int bestX = 0;
        int bestY = 0;

        if(MyArchonX < MapSizeX/3){
            bestX = MapSizeX;
        }
        if(MyArchonY < MapSizeY/3){
            bestY = MapSizeY;
        }
        return new MapLocation(bestX,bestY);
    }

    public static void RunToFightAnotherDay() throws GameActionException {

        if(!rc.getLocation().isWithinDistanceSquared(MyArchon, RobotType.ARCHON.actionRadiusSquared)){
            Direction runDir = rc.getLocation().directionTo(MyArchon);
            if(rc.canMove(runDir)){
                rc.move(runDir);
                rc.setIndicatorString("OH SHIT IM RUNNING!!!" + MyArchon + " Squared " + rc.getLocation().distanceSquaredTo(MyArchon) + " " + runDir);
            }else{
                Direction ranDir = directions[rng.nextInt(directions.length-1)];
                if(rc.canMove(ranDir)) {
                    rc.move(ranDir);
                    rc.setIndicatorString("OH SHIT IM RUNNING!!!" + MyArchon + " Squared " + rc.getLocation().distanceSquaredTo(MyArchon) + " random? " + ranDir);
                }
            }
        }
    }

    public static void TryAttack() throws GameActionException {
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
                if (rc.canEnvision(AnomalyType.CHARGE)) {
                    rc.envision(AnomalyType.CHARGE);
                }
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
    }
//    public static boolean CanAttackTarget(){
//        if (rc.canAttack()) {
//            if (!me.isBuilding()) {
//                if (robot.type.isBuilding()) {
//                    tryMoveTo(robot.location);
//                } else {
//                    int currentRubble = rc.senseRubble(rc.getLocation());
//
//                    for (Direction direction : adjacentDirections) {
//                        if (!rc.canMove(direction)) {
//                            continue;
//                        }
//
//                        MapLocation newLocation = rc.adjacentLocation(direction);
//                        if (newLocation.distanceSquaredTo(robot.location) > me.actionRadiusSquared) {
//                            continue;
//                        }
//
//                        if (rc.senseRubble(newLocation) >= currentRubble) {
//                            continue;
//                        }
//
//                        if (tryMove(direction)) {
//                            break;
//                        }
//                    }
//                }
//            }
//
//            boolean didAttack = false;
//
//            if (rc.canEnvision(AnomalyType.CHARGE)) {
//                int chargeDamage = 0;
//
//                for (RobotInfo enemyRobot : rc.senseNearbyRobots(me.actionRadiusSquared, enemyTeam)) {
//                    if (!enemyRobot.type.isBuilding()) {
//                        chargeDamage += Math.min(enemyRobot.health, Math.floor((double) enemyRobot.type.getMaxHealth(enemyRobot.level) / 100.0 * 22.0));
//                    }
//                }
//
//                if (chargeDamage > me.damage) {
//                    rc.envision(AnomalyType.CHARGE);
//                    didAttack = true;
//                }
//            }
//
//            if (!didAttack && rc.canAttack(robot.location)) {
//                rc.attack(robot.location);
//                didAttack = true;
//            }
//
//            if (didAttack && robot.type == RobotType.ARCHON) {
//                RobotInfo newRobot = rc.senseRobotAtLocation(robot.location);
//                if (newRobot == null || newRobot.team == myTeam || newRobot.type != RobotType.ARCHON) {
//                    sharedArray.setEnemyArchonLocation(sharedArray.archonIdToIndex(robot.ID), null);
//                }
//            }
//
//            if (robot.type.canAttack() && rc.isMovementReady() && rc.getHealth() != me.getMaxHealth(rc.getLevel())) {
//                tryMoveToSafety();
//            }
//
//            return true;
//        }
//
//        return false;
//    }
//    }

    protected static MapLocation[] findMinesByDistance(int distance) throws GameActionException {
        MapLocation[] tmpLeadML = rc.senseNearbyLocationsWithLead(distance);
        MapLocation[] tmpGoldML = rc.senseNearbyLocationsWithGold(distance);
        MapLocation[] Mines = new MapLocation[tmpLeadML.length + tmpGoldML.length];
        System.arraycopy(tmpLeadML, 0, Mines, 0, tmpLeadML.length);
        System.arraycopy(tmpGoldML, 0, Mines, tmpLeadML.length, tmpGoldML.length);
        if(Mines.length < 1) {
            if(rc.readSharedArray(MINE_1_X.getIndex()) != 0  && rc.readSharedArray(MINE_1_Y.getIndex()) != 0 ) {
                Mines = new MapLocation[]{
                        new MapLocation(rc.readSharedArray(MINE_1_X.getIndex()), rc.readSharedArray(MINE_1_Y.getIndex()))
                };
            }
            return Mines;
        }else{
            rc.writeSharedArray(MINE_1_X.getIndex(),Mines[0].x);
            rc.writeSharedArray(MINE_1_X.getIndex(),Mines[0].y);
        }
        return Mines;
    }
}
