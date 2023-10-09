package com.grizzly.application.views;

import java.awt.*;

public class ThemeManager {

    private static ThemeManager instance = null;
    private Color primary;

    private Color secondary;
    private Color neutral1;
    private Color accent1;
    private Color neutral2;
    private Color accent2;

    private ThemeManager() {
        primary = Color.BLUE;
        secondary = Color.PINK;
        accent1 = Color.YELLOW;
        accent2 = Color.RED;
        neutral1 = Color.BLACK;
        neutral2 = Color.WHITE;
    }

    public static synchronized ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public ThemeManager setColorScheme(Color primary, Color secondary, Color neutral1, Color accent1, Color neutral2, Color accent2) {
        this.primary = primary;
        this.secondary = secondary;
        this.neutral1 = neutral1;
        this.accent1 = accent1;
        this.neutral2 = neutral2;
        this.accent2 = accent2;

        return instance;
    }

    public Color getPrimary() {
        return primary;
    }

    public void setPrimary(Color primary) {
        this.primary = primary;
    }

    public Color getSecondary() {
        return secondary;
    }

    public void setSecondary(Color secondary) {
        this.secondary = secondary;
    }

    public Color getNeutral1() {
        return neutral1;
    }

    public void setNeutral1(Color neutral1) {
        this.neutral1 = neutral1;
    }

    public Color getAccent1() {
        return accent1;
    }

    public void setAccent1(Color accent1) {
        this.accent1 = accent1;
    }

    public Color getNeutral2() {
        return neutral2;
    }

    public void setNeutral2(Color neutral2) {
        this.neutral2 = neutral2;
    }

    public Color getAccent2() {
        return accent2;
    }

    public void setAccent2(Color accent2) {
        this.accent2 = accent2;
    }

    @Override
    public String toString() {
        return "ThemeManager{" +
                "primary=" + primary +

                ", secondary=" + secondary +
                ", neutral1=" + neutral1 +
                ", accent1=" + accent1 +
                ", neutral2=" + neutral2 +
                ", accent2=" + accent2 +
                '}';
    }
}
