package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class Album implements Serializable {
    private String name;
    private List<Photo> photos;
    private Photo oldestPhoto;
    private Photo earliestPhoto;

    /**
     * Album constructor
     *
     * @param name name
     */
    public Album(String name) {
        this.name = name;
        oldestPhoto = null;
        earliestPhoto = null;
        photos = new ArrayList<Photo>();
    }


    /**
     * Set name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Add a photo
     *
     * @param photo photo
     */
    public void addPhoto(Photo photo) {
        photos.add(photo);
        findOldestPhoto();
        findEarliestPhoto();

    }

    /**
     * Remove photo
     *
     * @param index index
     */
    public void removePhoto(int index) {
        photos.remove(index);
        findOldestPhoto();
        findEarliestPhoto();
    }

    /**
     * Get photo
     *
     * @param index index
     * @return photo
     */
    public Photo getPhoto(int index) {
        return photos.get(index);
    }


    /**
     * Get count
     *
     * @return count
     */
    public int getCount() {
        return photos.size();
    }

    /**
     * Find the oldest photo
     */
    public void findOldestPhoto() {
        if (photos.size() == 0)
            return;

        Photo temp = photos.get(0);

        for (Photo p : photos)
            if (p.getCalendar().compareTo(temp.getCalendar()) < 0)
                temp = p;

        oldestPhoto = temp;
    }


    /**
     * Find the earliest photo
     */
    public void findEarliestPhoto() {
        if (photos.size() == 0)
            return;

        Photo temp = photos.get(0);

        for (Photo p : photos)
            if (p.getCalendar().compareTo(temp.getCalendar()) > 0)
                temp = p;

        earliestPhoto = temp;
    }

    /**
     * Get the oldest date
     *
     * @return date string
     */
    public String getOldestPhotoDate() {
        if (oldestPhoto == null)
            return "NA";
        return oldestPhoto.getDate();
    }


    /**
     * Get the earliest date
     *
     * @return date string
     */
    public String getEarliestPhotoDate() {
        if (earliestPhoto == null)
            return "NA";
        return earliestPhoto.getDate();
    }

    /**
     * Get date range
     *
     * @return range string
     */
    public String getDateRange() {
        return getOldestPhotoDate() + " - " + getEarliestPhotoDate();
    }


    /**
     * Get image
     *
     * @return image
     */
    public Image getAlbumPhoto() {
        if (photos.isEmpty())
            return null;
        return photos.get(0).getImage();
    }

    /**
     * Get photo list
     *
     * @return photo list
     */
    public List<Photo> getPhotos() {
        return photos;
    }


    /**
     * Get index by photo
     *
     * @param photo photo
     * @return index
     */
    public int findIndexByPhoto(Photo photo) {
        for (int i = 0; i < photos.size(); i++)
            if (photos.get(i).equals(photo))
                return i;
        return -1;
    }
}
