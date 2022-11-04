package ColdPocket.Bots;

import ColdPocket.Bots.Base.Droid;
import battlecode.common.*;


public class Archon extends Droid {
    public static void TakeTurn() throws GameActionException {
        tryBuildAny();
        ArchonHeal();
        getArchonLocation();
    }

    private static void getArchonLocation() throws GameActionException {
        rc.writeSharedArray(19, rc.getLocation().x);
        rc.writeSharedArray(20,rc.getLocation().y);
    }




    static Direction randomDir = directions[rng.nextInt(directions.length)];
    private static void Build(RobotController rc,String type, Direction dir)throws GameActionException {
        if(type.equalsIgnoreCase("miner") && rc.canBuildRobot(RobotType.MINER, dir)){
            rc.buildRobot(RobotType.MINER, dir);
            rc.setIndicatorString("Making: " + type + " | Round Num: " + rc.getRoundNum());
            MinersAlive++;
        }
        else if(type.equalsIgnoreCase("soldier")&& rc.canBuildRobot(RobotType.SOLDIER, dir)){
            rc.buildRobot(RobotType.SOLDIER, dir);
            rc.setIndicatorString("Making: " + type + " | Round Num: " + rc.getRoundNum());
            SoldiersAlive++;
        }

        else if(type.equalsIgnoreCase("laboratory") && rc.canBuildRobot(RobotType.LABORATORY, dir)){
            rc.buildRobot(RobotType.LABORATORY, dir);
            rc.setIndicatorString("Making: " + type + " | Round Num: " + rc.getRoundNum());
            LaboratorysAlive++;
        }

        else if(type.equalsIgnoreCase("watchtower") && rc.canBuildRobot(RobotType.WATCHTOWER, dir)){
            rc.buildRobot(RobotType.WATCHTOWER, dir);
            rc.setIndicatorString("Making: " + type + " | Round Num: " + rc.getRoundNum());
            WatchTowersAlive++;
        }

        else if(type.equalsIgnoreCase("builder") && rc.canBuildRobot(RobotType.BUILDER, dir)){
            rc.buildRobot(RobotType.BUILDER, dir);
            rc.setIndicatorString("Making: " + type + " | Round Num: " + rc.getRoundNum());
            BuildersAlive++;
        }

        else if(type.equalsIgnoreCase("sage") && rc.canBuildRobot(RobotType.SAGE, dir)){
            rc.buildRobot(RobotType.SAGE, dir);
            rc.setIndicatorString("Making: " + type + " | Round Num: " + rc.getRoundNum());
            SagesAlive++;

        }
        else{
            if(rc.getActionCooldownTurns() == 0){
                rc.setIndicatorString("Can't afford " + type);
            }
        }

    }

    private static void ArchonMove(Direction Dir) throws GameActionException {
            if(rc.canMove(Dir)){
                rc.move(Dir);
                rc.transform();
                rc.setIndicatorString("Moved | " + Dir);
            }
            else{
                rc.setIndicatorString("I can't move " + Dir);
            }
    }

    private static void tryBuildAny() throws GameActionException {
        if(MinersAlive <= 3 && rc.getRoundNum() <= 100 || MinersAlive < SoldiersAlive/2){
            Build(rc,"miner", randomDir);
        }
        else if(SoldiersAlive < MinersAlive * 2 || rc.getRoundNum() <= 100 || rc.canBuildRobot(RobotType.SOLDIER, randomDir)){
            if(rc.canBuildRobot(RobotType.SOLDIER, randomDir)){
                Build(rc,"soldier", randomDir);
            }
        }
        else{
            rc.setIndicatorString("Can't Afford anything, Waiting");
        }
    }

    private static void ArchonGoCorner() throws GameActionException {
        if(rc.getLocation().x < rc.getMapWidth()/2 && rc.canTransform()){
            ArchonMove(Direction.WEST);
        }
        else if(rc.getLocation().x > rc.getMapWidth()/2 && rc.canTransform()){
            ArchonMove(Direction.EAST);
        }
        else if(rc.getLocation().y < rc.getMapHeight()/2 && rc.canTransform()){
            ArchonMove(Direction.SOUTH);
        }
        else if(rc.getLocation().y > rc.getMapHeight()/2 && rc.canTransform()){
            ArchonMove(Direction.NORTH);
        }
    }




}
