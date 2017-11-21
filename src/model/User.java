package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User implements Serializable {
	private static final long serialVersionUID = -5880859658114087536L;
	private String username;
	private List<Album> albums;

	public User(String name) {
		username=name;
		albums = new ArrayList<Album>();
	}
	
	public String getName() {
		return username;
	}
	
	public void setName(String name) {
		username=name;
	}
	
	public List<Album> getAlbums() {
		return albums;
	}
	
	public void addAlbum(String albumName) {
		albums.add(new Album(albumName));
	}
	
	public void addAlbum(Album a) {
		albums.add(a);
	}
	
	public void addPhotoToAlbum(Photo p, int albumIndex) {
		albums.get(albumIndex).addPhoto(p);
	}
	
	public int getAlbumIndexByAlbum(Album a) {
		for (int i = 0; i < albums.size(); i++)
			if (albums.get(i).getName().equals(a.getName()))
				return i;
		return -1;
	}
	
	public Album getAlbumByName(String name) {
		for(Album a : albums)
		{
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
	
	public void removeAlbum(Album album){
		albums.remove(album);
	}
	
}
