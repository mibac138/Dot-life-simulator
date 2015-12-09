package com.mibac.dots.wen.util;

public class Logger {
    public static final int ENERGY = 0;
    public static final int DEATH = 1;
    public static final int AI = 2;
    public static final int MOVE = 3;
    public static final int OTHER = 4;
    public static final int INPUT = 5;
    public static final int BREED = 6;

    static boolean[] log;

    public Logger() {
        Logger.log = new boolean[7];
        Logger.log[0] = Debug.PRINT_ENERGY;
        Logger.log[1] = Debug.PRINT_DEATH;
        Logger.log[2] = Debug.PRINT_AI;
        Logger.log[3] = Debug.PRINT_MOVE;
        Logger.log[4] = Debug.PRINT_OTHER;
        Logger.log[5] = Debug.PRINT_INPUT;
        Logger.log[6] = Debug.PRINT_BREED;
    }

    public static void log(String message, int type) {
        if (log[type])
            if (type == ENERGY)
                System.out.println("ENERGY " + message);
            else if (type == DEATH)
                System.out.println("DEATH  [" + message + "]");
            else if (type == AI)
                System.out.println("AI     " + message);
            else if (type == MOVE)
                System.out.println("MOVE   " + message);
            else if (type == OTHER)
                System.out.println("OTHER  " + message);
            else if (type == INPUT)
                System.out.println("INPUT  " + message);
            else if (type == BREED)
                System.out.println("BREED  " + message);
            else
                System.out.println("UNKNOWN! " + message);
    }
}
