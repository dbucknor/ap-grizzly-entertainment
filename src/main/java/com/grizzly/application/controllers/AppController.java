package com.grizzly.application.controllers;

import com.grizzly.application.theme.ColorScheme;
import com.grizzly.application.theme.FontLoader;
import com.grizzly.application.theme.FontLoaderException;
import com.grizzly.application.theme.ThemeManager;
import com.grizzly.application.views.MainWindow;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class AppController {
    private MainWindow mainWindow;
    private ThemeManager themeManager;
    private final ClassLoader loader = AppController.class.getClassLoader();


    public AppController() {
        themeManager = ThemeManager.getInstance();
        setTheme();
        mainWindow = new MainWindow();
    }

    //set up theme
    private void setTheme() {
        String[] fontUrls = new String[]{"media/fonts/Gobold-Bold.ttf", "media/fonts/Gobold-CUTS.ttf", "media/fonts/Gobold-Regular.ttf", "media/fonts/Montserrat-Bold.ttf", "media/fonts/Montserrat-Light.ttf"};

        try {
            FontLoader fontLoader = new FontLoader(fontUrls);
            themeManager.setFontLoader(fontLoader);
        } catch (FontLoaderException e) {
            //TODO: Handle Exception
            throw new RuntimeException(e);
        }

        setColorSchemes();
    }

    private void setColorSchemes() {
        Color primary = new Color(0, 39, 97);
        Color secondary = new Color(0, 129, 207);
        Color accent1 = new Color(230, 244, 241);
        Color accent2 = new Color(255, 0, 0);
        Color neutralDark = new Color(0, 0, 0);
        Color neutralLight = new Color(255, 255, 255);

        ColorScheme light = new ColorScheme(primary, secondary, accent1, accent2, neutralDark, neutralLight);

        themeManager.setCurrentScheme(light);
        themeManager.setLightScheme(light);
        themeManager.setUseDarkTheme(false);
    }

    public void run() {

    }

}
