package com.projetjava.View;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private static ImageCache instance;
    private Map<String, Image> imageCache;

    private ImageCache() {
        imageCache = new HashMap<>();
    }

    public static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public Image getImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        } else {
            Image image = new Image(getClass().getResourceAsStream(path));
            if (!image.isError()) {
                imageCache.put(path, image);
            } else {
                System.err.println("Error loading image: " + path);
            }
            return image;
        }
    }
}