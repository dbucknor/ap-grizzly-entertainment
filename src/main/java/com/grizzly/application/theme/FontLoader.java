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
    private final Logger logger = LogManager.getLogger(FontLoader.class);
    private final ClassLoader loader = FontLoader.class.getClassLoader();
    private ArrayList<Font> fonts;
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

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
    public void loadFonts(String[] urls) throws FontLoaderException {

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
}

