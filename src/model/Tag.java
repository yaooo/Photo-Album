package model;
import java.io.Serializable;

public class Tag implements Serializable {
	private String type;
	private String value;
	
	public Tag(String t, String v) {
		type=t;
		value=v;
	}
	
	public String getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setType(String t) {
		type=t;
	}
	
	public void setValue(String v) {
		value=v;
	}
	
	public String toString() {
		return type + ": " + value;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode()+type.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof Tag))
			   return false;

		Tag t =(Tag ) obj;

        return t.getValue().equals(value) && t.getType().equals(type);
	}

}
