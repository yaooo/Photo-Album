package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class UserList implements Serializable {
	private static final long serialVersionUID = -6450606409568802896L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	private List<User> users;
	
	public UserList() {
		users = new ArrayList<User>();
	}
	public List<User> getUserList(){
		  return users;
	}
	
	public void addUser(User u) {
		users.add(u);
	}
	
	public void removeUser(User u) {
		if(u==null) {
			return;
		}
		users.remove(u);
	}
	public void removeUser(String s) {
		User toRemove=null;
		for(User u:users) {
			if(u.getName().equals(s)) {
				toRemove=u;
				break;
			}
		}
		users.remove(toRemove);
	}
	public boolean inList(String name) {
		for(User u:users) {
			if(u.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	public User getUserByUsername(String username) {
		  for (User u : users)
		  {
			  if (u.getName().equals(username))
				  return u;
		  }
		  return null;
	  }
	public String toString() {
		if (users == null) {
			return "no users";
		}
		String output = "";
		for(User u : users){
			  output += u.getName() + " ";
		}
		return output;
	}
	
	public static UserList read() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		UserList ulist = (UserList) ois.readObject();
		ois.close();
		return ulist;
	}
	public static void write (UserList ulist) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(ulist);
		oos.close();
	}
}
