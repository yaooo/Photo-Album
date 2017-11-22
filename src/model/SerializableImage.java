package model;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class SerializableImage implements Serializable {

    private int width, height;
    private int[][] pixels;

    /**
     * SerializableImage constructor
     */
    public SerializableImage() {
    }


    /**
     * Set image
     *
     * @param image images
     */
    public void setImage(Image image) {
        width = ((int) image.getWidth());
        height = ((int) image.getHeight());
        pixels = new int[width][height];

        PixelReader r = image.getPixelReader();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                pixels[i][j] = r.getArgb(i, j);
    }


    /**
     * Get images
     *
     * @return image
     */
    public Image getImage() {
        WritableImage image = new WritableImage(width, height);

        PixelWriter w = image.getPixelWriter();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                w.setArgb(i, j, pixels[i][j]);

        return image;
    }


    /**
     * Image gets width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }


    /**
     * Image gets height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get pixels
     *
     * @return
     */
    public int[][] getPixels() {
        return pixels;
    }


    /**
     * If two images are the same
     *
     * @param si image
     * @return if the same
     */
    public boolean equals(SerializableImage si) {
        if (width != si.getWidth())
            return false;
        if (height != si.getHeight())
            return false;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (pixels[i][j] != si.getPixels()[i][j])
                    return false;
        return true;
    }

}

