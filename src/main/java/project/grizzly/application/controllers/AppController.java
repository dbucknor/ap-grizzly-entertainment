package project.grizzly.application.controllers;

import project.grizzly.application.theme.ColorScheme;
import project.grizzly.application.theme.FontLoader;
import project.grizzly.application.theme.FontLoaderException;
import project.grizzly.application.theme.ThemeManager;
import project.grizzly.application.views.screens.MainWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.awt.*;

/**
 * Main controller for client application
 */
@Controller
public class AppController {
    private MainWindow mainWindow;
    private final ThemeManager themeManager;
    private final Logger logger = LogManager.getLogger(AppController.class);

    public AppController() {
        themeManager = ThemeManager.getInstance();
        setTheme();

        Thread thread = new Thread(() -> {
            mainWindow = MainWindow.getInstance();
        });

        thread.start();
    }

    //set up theme
    private void setTheme() {
//        String[] fontUrls = new String[]{"media/fonts/Gobold-Bold.ttf", , , "media/fonts/Montserrat-Bold.ttf", };
        try {
            FontLoader fontLoader = new FontLoader();

            fontLoader.setH1(FontLoader.loadFont("media/fonts/Gobold-Regular.ttf").deriveFont(Font.BOLD, 22));
            fontLoader.setH2(FontLoader.loadFont("media/fonts/Montserrat-Regular.ttf").deriveFont(Font.BOLD, 18));
            fontLoader.setH3(FontLoader.loadFont("media/fonts/Montserrat-Regular.ttf").deriveFont(Font.BOLD, 16));

            fontLoader.setLIGHT(FontLoader.loadFont("media/fonts/Montserrat-Light.ttf").deriveFont(Font.PLAIN, 16));
            fontLoader.setBODY(FontLoader.loadFont("media/fonts/Montserrat-Regular.ttf").deriveFont(Font.PLAIN, 16));
            fontLoader.setLOGO(FontLoader.loadFont("media/fonts/Gobold-CUTS.ttf").deriveFont(Font.BOLD, 22));

            themeManager.setFontLoader(fontLoader);
            logger.info("Fonts Updated");

        } catch (FontLoaderException e) {
            logger.error(e.getMessage());
        }

        setColorSchemes();
        logger.info("Color Schemes Updated");
        logger.info("Theme Updated");
    }

    private void setColorSchemes() {
        Color primary = new Color(0, 39, 97);
        Color secondary = new Color(0, 129, 207);
        Color accent1 = new Color(230, 244, 241);
        Color accent2 = new Color(255, 0, 0);
        Color neutralDark = new Color(0, 0, 0);
        Color neutralLight = new Color(255, 255, 255);

        ColorScheme light = new ColorScheme(primary, secondary, accent1, accent2, neutralDark, neutralLight);

        ColorScheme dark = new ColorScheme(primary, secondary, accent1, accent2, neutralDark, neutralLight);

        themeManager.setCurrentScheme(light);
        themeManager.setLightScheme(light);
        themeManager.setDarkScheme(dark);
        themeManager.setUseDarkTheme(false);
    }

}
