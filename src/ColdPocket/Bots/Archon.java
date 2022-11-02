package ColdPocket.Bots;

import ColdPocket.Bots.Base.Droid;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotType;



public class Archon extends Droid {

    static int MinersAlive = 0;
    static int SoldiersAlive = 0;
    static int LaboratorysAlive = 0;
    static int WatchTowersAlive = 0;
    static int BuildersAlive = 0;
    static int SagesAlive = 0;


    public static void TakeTurn() throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
        if (MinersAlive < 9 && SoldiersAlive > 1 || MinersAlive < 9 && rc.getRoundNum() == 1) {
            if (rc.canBuildRobot(RobotType.MINER, dir)) {
                Spawn("miner");
                MinersAlive++;
            }
            else{
                rc.setIndicatorString("Can't Afford anything, Waiting");
            }
        }
        else {
            if (rc.canBuildRobot(RobotType.LABORATORY, dir)){
                Spawn("laboratory");
                LaboratorysAlive++;
            }
            else if (rc.canBuildRobot(RobotType.SOLDIER, dir)) {
                Spawn("soldier");
                SoldiersAlive++;
            }
            else{
                rc.setIndicatorString("Can't Afford anything, Waiting");
            }
        }
    }




    static Direction dir = directions[rng.nextInt(directions.length)];
    private static void Spawn(String type)throws GameActionException {

        if(type.equalsIgnoreCase("miner")){
            rc.buildRobot(RobotType.MINER, dir);
            rc.setIndicatorString("Trying to build a " + type);
        }
        if(type.equalsIgnoreCase("soldier")){
            rc.buildRobot(RobotType.SOLDIER, dir);
            rc.setIndicatorString("Trying to build a " + type);
        }
        if(type.equalsIgnoreCase("laboratory")){
            rc.buildRobot(RobotType.LABORATORY, dir);
            rc.setIndicatorString("Trying to build a " + type);
        }
        if(type.equalsIgnoreCase("watchtower")){
            rc.buildRobot(RobotType.WATCHTOWER, dir);
            rc.setIndicatorString("Trying to build a " + type);
        }
        if(type.equalsIgnoreCase("builder")){
            rc.buildRobot(RobotType.BUILDER, dir);
            rc.setIndicatorString("Trying to build a " + type);
        }
        if(type.equalsIgnoreCase("sage")){
            rc.buildRobot(RobotType.SAGE, dir);
            rc.setIndicatorString("Trying to build a " + type);
        }
    }



}
