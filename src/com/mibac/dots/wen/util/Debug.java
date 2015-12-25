package com.mibac.dots.wen.util;

import java.util.ArrayList;
import java.util.List;

import com.mibac.dots.wen.Creatures;

public enum Debug {
    DEBUG(true, null),

    DRAW(true, DEBUG), //
    DRAW_PATH(true, DRAW), DRAW_VISION(true, DRAW), DRAW_BORDER(true, DRAW),

    PRINT(false, DEBUG), //
    PRINT_ENERGY(true, PRINT), PRINT_DEATH(true, PRINT), PRINT_AI(true, PRINT), //
    PRINT_BREED(true, PRINT), PRINT_MOVE(true, PRINT), PRINT_INPUT(true, PRINT), //
    PRINT_OTHER(true, PRINT),

    CMD(true, DEBUG);

    private final List<Debug> hierarchy;
    private final String saveName;
    private boolean thisEnabled;
    private Debug parent;

    private Debug(boolean thisEnabled, Debug parent) {
        List<Debug> list = new ArrayList<>();
        if (parent != null)
            list.addAll(parent.getHierarchy());
        list.add(this);
        hierarchy = list;

        String s = "";
        if (list.size() < 3) {
            s = getAll(0, list.size(), list);
            s = s.substring(0, s.length() - 1);
        } else {
            String last = list.get(list.size() - 1).name();
            String prev = list.get(list.size() - 2).name();
            last = last.substring(prev.length() + 1, last.length());

            s = getAll(0, list.size() - 2, list) + prev + "." + last;
        }
        saveName = s.toLowerCase();

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
        Creatures.getConfig().set(saveName, thisEnabled);
    }

    public List<Debug> getHierarchy() {
        return hierarchy;
    }

    public String getSaveName() {
        return saveName;
    }

    @Override
    public String toString() {
        return parent == null ? name() : parent.toString() + "." + name();
    }

    private String getAll(int start, int end, List<Debug> list) {
        String s = "";
        for (int i = start; i < end; i++)
            s += list.get(i).name() + ".";
        return s;
    }
}
