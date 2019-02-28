package model;


/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 28/02/2019
 */
public class Photo {

    private int id;
    private int orientation;
    private int tagsNo;
    private String[] tags;

    public Photo(int id, int orientation, int tagsNo, String[] tags) {
        this.id = id;
        this.orientation = orientation;
        this.tagsNo = tagsNo;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public int getOrientation() {
        return orientation;
    }

    public String[] getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Photo id: " + id + ", orientation: " + (orientation == 0 ? "H" : "V") + ", tagsNo: " + tagsNo;
    }
}
