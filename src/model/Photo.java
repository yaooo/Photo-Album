package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;

/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */
public class Photo implements Serializable {

    private static final long serialVersionUID = 7182084870297668581L;
    private SerializableImage image;
    private String caption;
    private List<Tag> tags;
    private Calendar cal;

    /**
     * Photo constructor
     */
    public Photo() {
        caption = "";
        tags = new ArrayList<Tag>();
        cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        image = new SerializableImage();
    }

    /**
     * Photo constructor
     *
     * @param i iamge
     */
    public Photo(Image i) {
        this();
        image.setImage(i);
    }


    /**
     * Add tag
     *
     * @param type  type
     * @param value value
     */
    public void addTag(String type, String value) {
        tags.add(new Tag(type, value));
    }


    /**
     * Edit tag
     *
     * @param index index
     * @param type  type
     * @param value value
     */
    public void editTag(int index, String type, String value) {
        tags.get(index).setType(type);
        tags.get(index).setValue(value);
    }

    /**
     * Remove tag
     *
     * @param index index
     */
    public void removeTag(int index) {
        tags.remove(index);
    }


    /**
     * Get tag
     *
     * @param index index
     * @return tag
     */
    public Tag getTag(int index) {
        return tags.get(index);
    }


    /**
     * Set caption
     *
     * @param caption caption string
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }


    /**
     * Get caption
     *
     * @return caption string
     */
    public String getCaption() {
        return caption;
    }


    /**
     * Get calendar
     *
     * @return calender
     */
    public Calendar getCalendar() {
        return cal;
    }


    /**
     * Get date
     *
     * @return date
     */
    public String getDate() {
        String[] str = cal.getTime().toString().split("\\s+");
        return str[0] + " " + str[1] + " " + str[2] + ", " + str[5];
    }


    /**
     * Get image
     *
     * @return image
     */
    public Image getImage() {
        return image.getImage();
    }


    /**
     * Get serializable image
     *
     * @return serializableimage
     */
    public SerializableImage getSerializableImage() {
        return image;
    }


    /**
     * If have subset
     *
     * @param tlist tag list
     * @return if have subset
     */
    public boolean hasSubset(List<Tag> tlist) {
        Set<Tag> allTags = new HashSet<Tag>();
        allTags.addAll(tags);

        for (Tag t : tlist) {
            if (!allTags.contains(t))
                return false;
        }

        return true;
    }


    public boolean isWithinDateRange(LocalDate fromDate, LocalDate toDate) {
        LocalDate date = cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return date.isBefore(toDate) && date.isAfter(fromDate) || date.equals(fromDate) || date.equals(toDate);
    }


    public List<Tag> getTags() {
        return tags;
    }

    public Photo carbonCopy() {
        Photo copy = new Photo(this.getImage());
        copy.tags = this.getTags();
        copy.cal = this.getCalendar();
        copy.caption = this.getCaption();
        return copy;
    }
}
