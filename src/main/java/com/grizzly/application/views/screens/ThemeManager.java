package com.grizzly.application.views.screens;

import java.awt.Color;

public class ThemeManager {
    private static ThemeManager instance;
    private Color backgroundColor;

    private ThemeManager() {
        // Initialization logic, e.g. fetching colors from a config
        this.backgroundColor = Color.WHITE;  // Placeholder color
    }

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
