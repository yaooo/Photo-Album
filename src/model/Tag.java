package model;

import java.io.Serializable;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class Tag implements Serializable {
    private String type;
    private String value;

    /**
     * Tag constructor
     *
     * @param t type
     * @param v value
     */
    public Tag(String t, String v) {
        type = t;
        value = v;
    }

    /**
     * Get type
     *
     * @return type string
     */
    public String getType() {
        return type;
    }

    /**
     * Get value
     *
     * @return value string
     */
    public String getValue() {
        return value;
    }

    /**
     * set type
     *
     * @param t type
     */
    public void setType(String t) {
        type = t;
    }

    /**
     * Set value
     *
     * @param v values
     */
    public void setValue(String v) {
        value = v;
    }

    /**
     * toString
     *
     * @return string
     */
    public String toString() {
        return type + ": " + value;
    }

    /**
     * hashCode
     *
     * @return
     */
    @Override
    public int hashCode() {
        return value.hashCode() + type.hashCode();
    }

    /**
     * If the same
     *
     * @param obj object
     * @return if the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tag))
            return false;

        Tag t = (Tag) obj;

        return t.getValue().equals(value) && t.getType().equals(type);
    }

}
