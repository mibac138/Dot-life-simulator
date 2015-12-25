package com.mibac.dots.wen.io;

import com.mibac.dots.wen.util.Debug;

public class DebugConfiguration extends ComponentConfiguration {
    public static final String PREFIX = "debug";

    public DebugConfiguration(CreaturesConfiguration config) {
        super(config);
    }

    public boolean get(Debug d) {
        return config.getBoolean(d.toString());
    }

    /**
     * To change actual debug value use {@link Debug#setEnabled(boolean)}
     */
    public void update(Debug d) {
        config.set(d.getSaveName(), d.isThisEnabled());
    }

    @Override
    public void save() {
        for (Debug d : Debug.values())
            update(d);
    }
}
