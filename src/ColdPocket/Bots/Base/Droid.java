package ColdPocket.Bots.Base;

import battlecode.common.*;

import java.util.Random;


public abstract class Droid {
    public static int MinersAlive = 0;
    public static int SoldiersAlive = 0;
    public static int LaboratorysAlive = 0;
    public static int WatchTowersAlive = 0;
    public static int BuildersAlive = 0;
    public static int SagesAlive = 0;

    public static RobotController rc;
    public static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };




    public static final Random rng = new Random(6147);
    public static void init(RobotController robotController){
        rc = robotController;

    }

    public static void moveRandomLocation() throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
        }
    }

    public static void moveToLocation(MapLocation Location) throws GameActionException {
        if (Location != null && rc.canMove(rc.getLocation().directionTo(Location))){
            rc.move(rc.getLocation().directionTo(Location));
        }
        else{
            moveRandomLocation();
        }
    }

    public static void checkForEnemy() throws GameActionException {
        int radius = rc.getType().actionRadiusSquared;
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
        for (RobotInfo Tower : enemies) {
            if (Tower.getType() == RobotType.ARCHON && Tower.team == opponent) {
                rc.writeSharedArray(1, Tower.getLocation().x);
                rc.writeSharedArray(2, Tower.getLocation().y);
                rc.writeSharedArray(13, Tower.getID());
                System.out.println("Archon Found | 1 & 2| " + Tower.getLocation().x + "," + Tower.getLocation().y);
            }
            if (Tower.getType() == RobotType.SOLDIER && Tower.team == opponent) {
                rc.writeSharedArray(3, Tower.getLocation().x);
                rc.writeSharedArray(4, Tower.getLocation().y);
                rc.writeSharedArray(14, Tower.getID());
                System.out.println("Soldier Found | 3 & 4  | " + Tower.getLocation().x + "," + Tower.getLocation().y);
            }
            if (Tower.getType() == RobotType.MINER && Tower.team == opponent) {
                rc.writeSharedArray(5, Tower.getLocation().x);
                rc.writeSharedArray(6, Tower.getLocation().y);
                rc.writeSharedArray(15, Tower.getID());
                System.out.println("Miner Found | 5 & 6 | " + Tower.getLocation().x + "," + Tower.getLocation().y);
            }
            if (Tower.getType() == RobotType.SAGE && Tower.team == opponent) {
                rc.writeSharedArray(7, Tower.getLocation().x);
                rc.writeSharedArray(8, Tower.getLocation().y);
                rc.writeSharedArray(16, Tower.getID());
                System.out.println("Sage Found | 7 & 8 | " + Tower.getLocation().x + "," + Tower.getLocation().y);
            }
            if (Tower.getType() == RobotType.WATCHTOWER && Tower.team == opponent) {
                rc.writeSharedArray(9, Tower.getLocation().x);
                rc.writeSharedArray(10, Tower.getLocation().y);
                rc.writeSharedArray(17, Tower.getID());
                System.out.println("Watch Tower Found | 9 & 10 | " + Tower.getLocation().x + "," + Tower.getLocation().y);
            }
            if (Tower.getType() == RobotType.LABORATORY && Tower.team == opponent) {
                rc.writeSharedArray(11, Tower.getLocation().x);
                rc.writeSharedArray(12, Tower.getLocation().y);
                rc.writeSharedArray(18, Tower.getID());
                System.out.println("Laboratory Found | 11 & 12 | " + Tower.getLocation().x + "," + Tower.getLocation().y);
            }
        }
    }

    public static void attackAnything() throws GameActionException {
        Team opponent = rc.getTeam().opponent();
        RobotInfo[] enemies = rc.senseNearbyRobots(-1, opponent);
        if(enemies.length > 0){
            MapLocation MapToAttack = enemies[0].location;

            if (rc.canAttack(MapToAttack)) {
                rc.attack(MapToAttack);
            }
        }
    }

    public static MapLocation[] SearchMinerals() throws GameActionException {
        MapLocation[] NearGLocations = rc.senseNearbyLocationsWithGold(-1);
        MapLocation[] NearLLocations = rc.senseNearbyLocationsWithLead(-1);
        MapLocation[] MineLocations = new MapLocation[NearGLocations.length + NearLLocations.length];
        System.arraycopy(NearGLocations, 0, MineLocations, 0, NearGLocations.length);
        System.arraycopy(NearLLocations, 0, MineLocations, NearGLocations.length, NearLLocations.length);
        return MineLocations;
    }

    public static MapLocation lookForMinerals() throws GameActionException {
        MapLocation me = rc.getLocation();
        MapLocation[] MineralsVision = SearchMinerals();
        MapLocation ClosestMappoint = null;

        for (MapLocation MineralVision:MineralsVision){
            if (ClosestMappoint == null){
                ClosestMappoint = MineralVision;
            }
            if (MineralVision.compareTo(me) < ClosestMappoint.compareTo(me)){
                ClosestMappoint = MineralVision;
            }
        }
        if(ClosestMappoint != null){
            if (rc.getType() == RobotType.MINER){
                rc.setIndicatorString("Found Closest Mappoint " + ClosestMappoint + "| Health: " + rc.getHealth());
                rc.setIndicatorDot(ClosestMappoint, 255,0,255);
            }
        }
        else{
            return null;
        }
        return ClosestMappoint;
    }

    public static int checkInArray(int Exists, int minIndex, int maxIndex) throws GameActionException {
        while(minIndex <= maxIndex){
            int ShareArray = rc.readSharedArray(minIndex);
            minIndex++;
            if (ShareArray == Exists){
                return minIndex;
            }
        }
        return 0;
    }
    public static void setValues() throws GameActionException {
        for (int index = 0; index < 64; index++) {
            rc.writeSharedArray(index, 65535);
        }
    }

    public static void trySuicide(){
        if(rc.getHealth() < NearEnemyDamage()){
            rc.setIndicatorString("I died because my health was < " + NearEnemyDamage() +" | Health: " + rc.getHealth());
            System.out.println("I died because my health was < " + NearEnemyDamage() +" | Health: " + rc.getHealth());
            if(rc.getType() == RobotType.MINER){
                MinersAlive--;
            }
            else if(rc.getType() == RobotType.SOLDIER){
                SoldiersAlive--;
            }
            else if(rc.getType() == RobotType.LABORATORY){
                LaboratorysAlive--;
            }
            else if(rc.getType() == RobotType.WATCHTOWER){
                WatchTowersAlive--;
            }
            else if(rc.getType() == RobotType.BUILDER){
                BuildersAlive--;
            }
            else if(rc.getType() == RobotType.SAGE){
                SagesAlive--;
            }
            rc.disintegrate();
        }
    }

    public static int NearEnemyDamage(){
        RobotInfo[] robotsAround = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        for (RobotInfo Robot:robotsAround) {
            if(rc.getHealth() < Robot.type.getDamage(Robot.level) + 3){
                return Robot.type.getDamage(Robot.level) + 3;
            }
        }
        return 0;
    }

    public static int PlaySafe(){
        if(SoldiersAlive < 5 && rc.getRoundNum() < 100){
            return 1;
        }
        return 0;


    }


    public static boolean canAttack() throws GameActionException {
        int index = 1;
        while (true) {
            if (index > 11) {
                return true;
            }
            if (rc.readSharedArray(index) != 65535 && rc.readSharedArray(index + 1) != 65535) {

                if(rc.getLocation().distanceSquaredTo(new MapLocation(rc.readSharedArray(index), rc.readSharedArray(index + 1))) < 4){
                    rc.writeSharedArray(index, 65535);
                    rc.writeSharedArray(index + 1, 65535);
                    index = index + 2;
                    continue;
                }

                moveToLocation(new MapLocation(rc.readSharedArray(index), rc.readSharedArray(index + 1)));
                if(index == 1){
                    String TowerTarget="Archon";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget + " | ID: " + rc.readSharedArray(13));
                }
                else if(index == 3){
                    String TowerTarget="Soldier";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget + " | ID: " + rc.readSharedArray(14));
                }
                else if(index == 5){
                    String TowerTarget="Miner";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget + " | ID: " + rc.readSharedArray(15));
                }
                else if(index == 7){
                    String TowerTarget="Sage";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget + " | ID: " + rc.readSharedArray(16));
                }
                else if(index == 9){
                    String TowerTarget="Watch Tower";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget + " | ID: " + rc.readSharedArray(17));
                }
                else if(index == 11){
                    String TowerTarget="Laboratory";
                    rc.setIndicatorString("Moving towards " + rc.readSharedArray(index) + "," + rc.readSharedArray(index + 1) + " | Tower: " + TowerTarget + " | ID: " + rc.readSharedArray(18));
                }
                return false;
            }
            index = index + 2;
        }
    }




}
