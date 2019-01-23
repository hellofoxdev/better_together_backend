package com.sebastianfox.food.utils;

@SuppressWarnings("unused")
public class Debugger{

    private Integer level;

    private boolean isEnabled = true;

    public static void log(Object o) {
        if (o != null) {
            System.out.println("DEBUG (log): " + o.toString());
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}