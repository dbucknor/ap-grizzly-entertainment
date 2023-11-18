package project.grizzly.application.controllers;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class ResourceLoader {
    private static ClassLoader loader;

    public static ClassLoader setClass(Object _class) {
        loader = _class.getClass().getClassLoader();
        return loader;
    }

    public static File getFile(String path) {
        File file;
        try {
            file = new File(Objects.requireNonNull(loader.getResource(path)).toURI().getPath());
            return file;

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
