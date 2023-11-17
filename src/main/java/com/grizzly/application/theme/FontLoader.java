package com.grizzly.application.theme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Loads fonts that the application uses
 */
public class FontLoader {
    private static final Logger logger = LogManager.getLogger(FontLoader.class);
    private static final ClassLoader loader = FontLoader.class.getClassLoader();
    private static ArrayList<Font> fonts;
    private Font H1, H2, H3, BODY, LIGHT, LOGO;
    private static final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    /**
     * Default constructor
     */
    public FontLoader() {
        fonts = null;
    }

    /**
     * Loads fonts from resources folder of application.
     *
     * @param urls local path to fonts
     * @throws FontLoaderException
     */
    public FontLoader(String[] urls) throws FontLoaderException {
        this.loadFonts(urls);
    }

    /**
     * Loads fonts from resources folder of application.
     *
     * @param urls local path to fonts
     * @throws FontLoaderException
     */
    public static void loadFonts(String[] urls) throws FontLoaderException {

        try {
            for (String url : urls) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, new File(Objects.requireNonNull(loader.getResource(url)).toURI().getPath()));
                ge.registerFont(font);
                //fonts.add(font);
            }
            logger.info("Fonts updated successfully!");

        } catch (IOException | URISyntaxException | FontFormatException ex) {
            fonts = null;
            logger.error("Error Loading Fonts!\n\n" + ex.getMessage());
            throw new FontLoaderException(ex.getMessage());
        }
    }

    /**
     * Loads fonts from resources folder of application.
     *
     * @param url local path to fonts
     * @throws FontLoaderException font loader exception
     */
    public static Font loadFont(String url) throws FontLoaderException {

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(Objects.requireNonNull(loader.getResource(url)).toURI().getPath()));
            ge.registerFont(font);
            logger.info("Font loaded successfully!");
            return font;
        } catch (IOException | URISyntaxException | FontFormatException ex) {
            logger.error("Error Loading Font!");
            logger.error(ex.getMessage());
            throw new FontLoaderException(ex.getMessage());
        }
    }

    /**
     * Retrieves loaded fonts
     *
     * @return fonts
     * @see Font
     * @see ArrayList
     */
    public ArrayList<Font> getFonts() {
        return fonts;
    }

    /**
     * Sets fonts that were already loaded
     *
     * @param fonts
     */
    public void setFonts(ArrayList<Font> fonts) {
        this.fonts = fonts;
    }

    public Font getH1() {
        return H1;
    }

    public void setH1(Font h1) {
        H1 = h1;
    }

    public Font getH2() {
        return H2;
    }

    public void setH2(Font h2) {
        H2 = h2;
    }

    public Font getH3() {
        return H3;
    }

    public Font getLIGHT() {
        return LIGHT;
    }

    public void setLIGHT(Font LIGHT) {
        this.LIGHT = LIGHT;
    }

    public void setH3(Font h3) {
        H3 = h3;
    }

    public Font getBODY() {
        return BODY;
    }

    public void setBODY(Font BODY) {
        this.BODY = BODY;
    }

    public Font getLOGO() {
        return LOGO;
    }

    public void setLOGO(Font LOGO) {
        this.LOGO = LOGO;
    }
}

