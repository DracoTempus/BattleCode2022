package ColdPocket;

import ColdPocket.Bots.Archon;
import ColdPocket.Bots.Base.Droid;
import ColdPocket.Bots.Miner;
import ColdPocket.Bots.Soldier;
import battlecode.common.*;
import java.util.Random;

import static ColdPocket.Bots.Archon.setValues;

public strictfp class RobotPlayer {
    static final Random rng = new Random(6147);

    public static void run(RobotController rc) throws GameActionException {


        try{
            Droid.init(rc);
            if (rc.getRoundNum() == 1 && rc.getType() == RobotType.ARCHON){
                setValues();
            }
            while (true) {
                try {
                    switch (rc.getType()) {
                        case ARCHON:        Archon.TakeTurn();   break;
                        case MINER:         Miner.TakeTurn();    break;
                        case SOLDIER:       Soldier.TakeTurn();  break;
                        case LABORATORY:
                        case WATCHTOWER:
                        case BUILDER:
                        case SAGE:
                    }
                } catch (Exception e) {
                    System.out.println(rc.getType() + " Exception");
                    e.printStackTrace();

                } finally {
                    Clock.yield();
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
