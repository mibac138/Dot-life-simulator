package com.mibac.dots.wen.util;

import static com.mibac.dots.wen.util.Debug.Type.PRINT_AI;
import static com.mibac.dots.wen.util.Debug.Type.PRINT_BREED;
import static com.mibac.dots.wen.util.Debug.Type.PRINT_DEATH;
import static com.mibac.dots.wen.util.Debug.Type.PRINT_ENERGY;
import static com.mibac.dots.wen.util.Debug.Type.PRINT_INPUT;
import static com.mibac.dots.wen.util.Debug.Type.PRINT_MOVE;
import static com.mibac.dots.wen.util.Debug.Type.PRINT_OTHER;

public class Logger {
    public static void log(String message, Debug.Type type) {
        if (type.isEnabled())
            if (type == PRINT_ENERGY)
                System.out.println("ENERGY " + message);
            else if (type == PRINT_DEATH)
                System.out.println("DEATH  [" + message + "]");
            else if (type == PRINT_AI)
                System.out.println("AI     " + message);
            else if (type == PRINT_MOVE)
                System.out.println("MOVE   " + message);
            else if (type == PRINT_OTHER)
                System.out.println("OTHER  " + message);
            else if (type == PRINT_INPUT)
                System.out.println("INPUT  " + message);
            else if (type == PRINT_BREED)
                System.out.println("BREED  " + message);
            else
                System.out.println("UNKNOWN! " + message);
    }
}
