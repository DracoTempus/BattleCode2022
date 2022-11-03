package ColdPocket.Bots;

import ColdPocket.Bots.Base.Droid;
import battlecode.common.*;


public class Archon extends Droid {
    public static void TakeTurn() throws GameActionException {
        tryBuildAny();
    }




    static Direction randomDir = directions[rng.nextInt(directions.length)];
    private static void Build(String type, Direction dir)throws GameActionException {
        if(type.equalsIgnoreCase("miner") && rc.canBuildRobot(RobotType.MINER, dir)){
            rc.buildRobot(RobotType.MINER, dir);
            rc.setIndicatorString("Trying to build a " + type);
            MinersAlive++;
        }
        if(type.equalsIgnoreCase("soldier")&& rc.canBuildRobot(RobotType.SOLDIER, dir)){
            rc.buildRobot(RobotType.SOLDIER, dir);
            rc.setIndicatorString("Trying to build a " + type);
            SoldiersAlive++;
        }
        if(type.equalsIgnoreCase("laboratory") && rc.canBuildRobot(RobotType.LABORATORY, dir)){
            rc.buildRobot(RobotType.LABORATORY, dir);
            rc.setIndicatorString("Trying to build a " + type);
            LaboratorysAlive++;
        }
        if(type.equalsIgnoreCase("watchtower") && rc.canBuildRobot(RobotType.WATCHTOWER, dir)){
            rc.buildRobot(RobotType.WATCHTOWER, dir);
            rc.setIndicatorString("Trying to build a " + type);
            WatchTowersAlive++;
        }
        if(type.equalsIgnoreCase("builder") && rc.canBuildRobot(RobotType.BUILDER, dir)){
            rc.buildRobot(RobotType.BUILDER, dir);
            rc.setIndicatorString("Trying to build a " + type);
            BuildersAlive++;
        }
        if(type.equalsIgnoreCase("sage") && rc.canBuildRobot(RobotType.SAGE, dir)){
            rc.buildRobot(RobotType.SAGE, dir);
            rc.setIndicatorString("Trying to build a " + type);
            SagesAlive++;
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
        if(MinersAlive < 1 && rc.getRoundNum() < 50 || MinersAlive < 10 && SoldiersAlive / MinersAlive >= 2){
            Build("miner", randomDir);
        }
        else if(SoldiersAlive < MinersAlive * 2 || rc.getRoundNum() < 100){
            Build("soldier", randomDir);
        }
        else{
            rc.setIndicatorString("Can't Afford anything, Waiting");
        }
    }

    private static void ArchonGetSafe() throws GameActionException {
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
