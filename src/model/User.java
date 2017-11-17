package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User implements Serializable {
	private String username;
	
	public User(String name) {
		username=name;
	}
	
	public String getName() {
		return username;
	}
	
	public void setName(String name) {
		username=name;
	}

}
