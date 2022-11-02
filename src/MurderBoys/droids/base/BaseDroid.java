package MurderBoys.droids.base;

import battlecode.common.*;

import java.util.Random;

import static MurderBoys.droids.base.BaseDroid.INDEX.*;

public class BaseDroid {

    public static RobotController rc;
    public static RobotType rt;
    public static MapLocation noLocation = new MapLocation(65535,65535);
    public static MapLocation EnemyArchonsLocation = noLocation;
    public static MapLocation FirstEnemyLocation = noLocation;
    public static MapLocation MyArchon = noLocation;


    //BUILDER SPECIFIC//
    public static boolean WATCHTOWERBUILT = false;
    public static boolean LABORATORYBUILT = false;
    public static boolean ARCHONBUILT = false;

    ///ARCHON SPECIFIC//
    public static int BUILDLOCATION = 0;
    public static int MAX_MINERS = 10;
    public static int SoldiersBuilt = 0;
    public static int BuildersBuilt = 0;

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
}
