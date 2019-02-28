package model;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 28/02/2019
 */
public class Photo implements Comparable<Photo> {

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
    
    public int getTagsNo() {
    	return tagsNo;
    }

    @Override
    public String toString() {
        return "Photo id: " + id + ", orientation: " + (orientation == 0 ? "H" : "V") + ", tagsNo: " + tagsNo;
    }

	@Override
	public int compareTo(Photo p) {
		HashSet<String> set = new HashSet<String>();
		Collections.addAll(set, this.tags);
		
		int intersectionCount = 0;
		
		for (int i = 0; i < p.tagsNo; i++) {
			if (set.contains(p.tags[i])) {
				intersectionCount ++;
			}
		}
		
		return Math.min(Math.min(intersectionCount, this.tagsNo - intersectionCount), p.tagsNo - intersectionCount);
	}
}
