package com.mibac.dots.wen.io;

public abstract class ComponentConfiguration {
    protected CreaturesConfiguration config;

    public ComponentConfiguration(CreaturesConfiguration config) {
        this.config = config;
    }

    public abstract void save();
}
