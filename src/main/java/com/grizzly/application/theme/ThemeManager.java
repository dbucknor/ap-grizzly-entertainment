package com.grizzly.application.theme;

import java.io.Serializable;


/**
 * Theme manager controls the color, fonts and anything to deal with the uniforming the look of the application.
 */
public class ThemeManager implements Serializable {

    //Singleton instance reference
    private static ThemeManager instance = null;
    private FontLoader fontLoader;
    private boolean useDarkTheme;
    //Color scheme in use
    private ColorScheme currentScheme;
    //Dark scheme palette
    private ColorScheme darkScheme;
    //Light Scheme palette
    private ColorScheme lightScheme;

    /**
     * Private constructor to only allow one instance of this class to be created
     */
    private ThemeManager() {
        darkScheme = new ColorScheme();
        lightScheme = new ColorScheme();
        currentScheme = new ColorScheme();
        fontLoader = new FontLoader();
    }

    /**
     * Allows only one instance of ThemeManager to be created; Creates on if none exists;
     *
     * @return instance of ThemeManager
     */
    public static synchronized ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }


    /**
     * Mutators and Accessors
     */
    public FontLoader getFontLoader() {
        return fontLoader;
    }

    public void setFontLoader(FontLoader fontLoader) {
        this.fontLoader = fontLoader;
    }

    public boolean isUseDarkTheme() {
        return useDarkTheme;
    }

    public void setUseDarkTheme(boolean useDarkTheme) {
        this.useDarkTheme = useDarkTheme;
    }

    public ColorScheme getCurrentScheme() {
        return currentScheme;
    }

    public void setCurrentScheme(ColorScheme currentScheme) {
        this.currentScheme = currentScheme;
    }

    public ColorScheme getDarkScheme() {
        return darkScheme;
    }

    public void setDarkScheme(ColorScheme darkScheme) {
        this.darkScheme = darkScheme;
    }

    public ColorScheme getLightScheme() {
        return lightScheme;
    }

    public void setLightScheme(ColorScheme lightScheme) {
        this.lightScheme = lightScheme;
    }
}
