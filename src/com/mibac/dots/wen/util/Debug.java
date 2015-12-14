package com.mibac.dots.wen.util;

public class Debug {
    public enum Type {
        DEBUG(Debug.DEBUG, null),

        DRAW(Debug.DRAW, DEBUG), //
        DRAW_PATH(Debug.DRAW_PATH, DRAW), DRAW_VISION(Debug.DRAW_VISION, DRAW), //
        DRAW_BORDER(Debug.DRAW_BORDER, DRAW),

        PRINT(Debug.PRINT, DEBUG), //
        PRINT_ENERGY(Debug.PRINT_ENERGY, PRINT), PRINT_DEATH(Debug.PRINT_DEATH, PRINT), //
        PRINT_AI(Debug.PRINT_AI, PRINT), PRINT_BREED(Debug.PRINT_BREED, PRINT), //
        PRINT_MOVE(Debug.PRINT_MOVE, PRINT), PRINT_INPUT(Debug.PRINT_INPUT, PRINT), //
        PRINT_OTHER(Debug.PRINT_OTHER, PRINT),

        CMDS(Debug.CMDS, DEBUG);

        private boolean thisEnabled;
        private Type parent;

        private Type(boolean thisEnabled, Type parent) {
            this.thisEnabled = thisEnabled;
            this.parent = parent;
        }

        public boolean isThisEnabled() {
            return thisEnabled;
        }

        public boolean isEnabled() {
            return thisEnabled && (parent == null ? true : parent.isEnabled());
        }

        public void setEnabled(boolean thisEnabled) {
            this.thisEnabled = thisEnabled;
        }
    }

    private static final boolean DEBUG = true;

    private static final boolean DRAW = true;
    private static final boolean DRAW_PATH = true;
    private static final boolean DRAW_VISION = true;
    private static final boolean DRAW_BORDER = true;

    private static final boolean PRINT = false;
    private static final boolean PRINT_ENERGY = true;
    private static final boolean PRINT_DEATH = true;
    private static final boolean PRINT_AI = true;
    private static final boolean PRINT_BREED = true;
    private static final boolean PRINT_MOVE = true;
    private static final boolean PRINT_INPUT = true;
    private static final boolean PRINT_OTHER = true;

    private static final boolean CMDS = false;
}
