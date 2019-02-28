package model;


import java.util.Collections;
import java.util.HashSet;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 28/02/2019
 */
public class Photo implements Comparable<Photo> {

    private String id;
    private int tagsNo;
    private String[] tags;

    public Photo(String id, int tagsNo, String[] tags) {
        this.id = id;
        this.tagsNo = tagsNo;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public int getTagsNo() {
        return tagsNo;
    }

    public String[] getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Photo id: " + id + ", tagsNo: " + tagsNo;
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
