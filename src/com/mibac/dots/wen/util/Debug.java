package com.mibac.dots.wen.util;

public class Debug {
    public enum Type {
        DEBUG(Debug.DEBUG), DRAW(Debug.DRAW), DRAW_PATH(Debug.DRAW_PATH), DRAW_VISION(
                Debug.DRAW_VISION), DRAW_BORDER(Debug.DRAW_BORDER), PRINT(
                        Debug.PRINT), PRINT_ENERGY(Debug.PRINT_ENERGY), PRINT_DEATH(
                                Debug.PRINT_DEATH), PRINT_AI(Debug.PRINT_AI), PRINT_BREED(
                                        Debug.PRINT_BREED), PRINT_MOVE(
                                                Debug.PRINT_MOVE), PRINT_INPUT(
                                                        Debug.PRINT_INPUT), PRINT_OTHER(
                                                                Debug.PRINT_OTHER), CMDS(
                                                                        Debug.CMDS);
        private boolean enabled;

        private Type(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            System.out.println(enabled);
        }
    }

    private static final boolean DEBUG = true;

    private static final boolean DRAW = DEBUG && true;
    private static final boolean DRAW_PATH = DRAW && true;
    private static final boolean DRAW_VISION = DRAW && true;
    private static final boolean DRAW_BORDER = DRAW && true;

    private static final boolean PRINT = DEBUG && true;
    private static final boolean PRINT_ENERGY = PRINT && false;
    private static final boolean PRINT_DEATH = PRINT && false;
    private static final boolean PRINT_AI = PRINT && false;
    private static final boolean PRINT_BREED = PRINT && true;
    private static final boolean PRINT_MOVE = PRINT && false;
    private static final boolean PRINT_INPUT = PRINT && false;
    private static final boolean PRINT_OTHER = PRINT && false;

    private static final boolean CMDS = DEBUG && false;
}
