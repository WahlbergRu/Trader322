package com.trader322.trader.Utils;

import static com.trader322.trader.Utils.ConsoleColors.*;

public class Utils {

    public static boolean timeBorders (long timestamp, long currentTimestamp, long differentTimestamp){
        long differentTime = timestamp - currentTimestamp;
//        System.out.println(
//                YELLOW + (differentTime < 0) + " " + (differentTime > -differentTimestamp) + " " +
//                BLUE + differentTime + " " +
//                CYAN + differentTimestamp + " " +
//                RED + (differentTime + differentTimestamp) + " " +
//                YELLOW + (differentTime > -differentTimestamp) + " " +
//                YELLOW + (differentTime + differentTimestamp < 60000)
//        );
        boolean isExist = differentTime < 0 && differentTime > -differentTimestamp && differentTime + differentTimestamp < 60000;

//        if (isExist){
//            System.out.println(BLUE + timestamp + " " + RED + currentTimestamp + " " + CYAN + differentTime);
//        }

        return isExist;
    }

}
