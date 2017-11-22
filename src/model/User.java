package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = -5880859658114087536L;
    private String username;
    private List<Album> albums;

    /**
     * User
     *
     * @param name name
     */
    public User(String name) {
        username = name;
        albums = new ArrayList<Album>();
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return username;
    }

    /**
     * Set name
     *
     * @param name name
     */
    public void setName(String name) {
        username = name;
    }

    /**
     * Get album
     *
     * @return album
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Add album
     *
     * @param albumName album
     */
    public void addAlbum(String albumName) {
        albums.add(new Album(albumName));
    }

    /**
     * Add album
     *
     * @param a album
     */
    public void addAlbum(Album a) {
        albums.add(a);
    }

    /**
     * Add photo to album
     *
     * @param p          photo
     * @param albumIndex index
     */
    public void addPhotoToAlbum(Photo p, int albumIndex) {
        albums.get(albumIndex).addPhoto(p);
    }

    public int getAlbumIndexByAlbum(Album a) {
        for (int i = 0; i < albums.size(); i++)
            if (albums.get(i).getName().equals(a.getName()))
                return i;
        return -1;
    }

    /**
     * Get album by name
     *
     * @param name name
     * @return Album
     */
    public Album getAlbumByName(String name) {
        for (Album a : albums) {
            if (a.getName().equals(name))
                return a;
        }
        return null;
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
    }

}
