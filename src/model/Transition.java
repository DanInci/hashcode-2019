package model;

/**
 * @author Daniel Incicau, daniel.incicau@busymachines.com
 * @since 28/02/2019
 */
public class Transition implements Comparable<Transition> {

    private Photo p1;
    private Photo p2;

    public Transition(Photo p1, Photo p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    public Photo getP1() {
        return p1;
    }

    public Photo getP2() {
        return p2;
    }

    @Override
    public int compareTo(Transition o) {
        int firstScore = p1.compareTo(p2);
        int secondScore = o.p1.compareTo(o.p2);
        return secondScore - firstScore;
    }
}
