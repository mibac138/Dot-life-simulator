package com.mibac.dots.wen.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CreaturesConfiguration extends Configuration {
    private WorldConfiguration world;
    private DebugConfiguration debug;

    public CreaturesConfiguration(File file, Map<String, Object> defaults) throws IOException {
        super(file, defaults);
        this.world = new WorldConfiguration(this);
        this.debug = new DebugConfiguration(this);
    }

    public CreaturesConfiguration(File file) throws IOException {
        this(file, null);
        this.world = new WorldConfiguration(this);
        this.debug = new DebugConfiguration(this);
    }

    @Override
    public void save() throws IOException {
        // They can be actually null if save is invoked in Configuration initialization (in example
        // when config is created and default values are written)
        if (world != null)
            world.save();
        if (debug != null)
            debug.save();
        super.save();
    }

    public WorldConfiguration getWorld() {
        return world;
    }

    public DebugConfiguration getDebug() {
        return debug;
    }
}
