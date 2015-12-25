package com.mibac.dots.wen.io;

import com.mibac.dots.wen.controller.WorldController;

public class WorldConfiguration extends ComponentConfiguration {
    private static final String PREFIX = "world";
    private static final String MAX_SPEED = "maxSpeed";

    public WorldConfiguration(CreaturesConfiguration config) {
        super(config);
    }

    public int getMaxSpeed() {
        return config.getInt(get(MAX_SPEED));
    }

    /**
     * To change actual max speed value use {@link WorldController#MAX_SPEED} Controller does
     * <b>not</b> automatically invoke this method due to it's unnecessarnes. <i>//Yes I know it
     * isn't proper English</i>
     */
    public void updateMaxSpeed() {
        config.set(get(MAX_SPEED), WorldController.MAX_SPEED);
    }

    @Override
    public void save() {
        updateMaxSpeed();
    }

    private String get(String s) {
        return PREFIX + "." + s;
    }
}
