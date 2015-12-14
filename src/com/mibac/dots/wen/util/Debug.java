package com.mibac.dots.wen.util;

public class Debug {
    public enum Type {
        DEBUG(true, null),

        DRAW(true, DEBUG), //
        DRAW_PATH(true, DRAW), DRAW_VISION(true, DRAW), DRAW_BORDER(true, DRAW),

        PRINT(false, DEBUG), //
        PRINT_ENERGY(true, PRINT), PRINT_DEATH(true, PRINT), PRINT_AI(true, PRINT), //
        PRINT_BREED(true, PRINT), PRINT_MOVE(true, PRINT), PRINT_INPUT(true, PRINT), //
        PRINT_OTHER(true, PRINT),

        CMDS(true, DEBUG);

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
}
