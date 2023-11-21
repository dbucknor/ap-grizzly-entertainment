package project.grizzly.application.theme;

import java.awt.*;
import java.io.Serializable;

public class ColorScheme implements Serializable {
    private Color primary;
    private Color secondary;
    private Color accent1;
    private Color accent2;
    private Color neutralDark;
    private Color neutralLight;

    public ColorScheme() {
        this.primary = Color.CYAN;
        this.secondary = Color.PINK;
        this.accent1 = Color.GREEN;
        this.accent2 = Color.YELLOW;
        this.neutralDark = Color.BLACK;
        this.neutralLight = Color.WHITE;
    }

    public ColorScheme(Color primary, Color secondary, Color accent1, Color accent2, Color neutralDark, Color neutralLight) {
        this.primary = primary;
        this.secondary = secondary;
        this.accent1 = accent1;
        this.accent2 = accent2;
        this.neutralDark = neutralDark;
        this.neutralLight = neutralLight;
    }

    public ColorScheme(ColorScheme scheme) {
        this.primary = scheme.primary;
        this.secondary = scheme.secondary;
        this.accent1 = scheme.accent1;
        this.accent2 = scheme.accent2;
        this.neutralDark = scheme.neutralDark;
        this.neutralLight = scheme.neutralLight;
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

    public Color getAccent1() {
        return accent1;
    }

    public void setAccent1(Color accent1) {
        this.accent1 = accent1;
    }

    public Color getAccent2() {
        return accent2;
    }

    public void setAccent2(Color accent2) {
        this.accent2 = accent2;
    }

    public Color getNeutralDark() {
        return neutralDark;
    }

    public void setNeutralDark(Color neutralDark) {
        this.neutralDark = neutralDark;
    }

    public Color getNeutralLight() {
        return neutralLight;
    }

    public void setNeutralLight(Color neutralLight) {
        this.neutralLight = neutralLight;
    }
}
