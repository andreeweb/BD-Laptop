package it.uniroma2.dicii.bd.bean;

import java.util.Comparator;

public class StarBeanComparator implements Comparator<StarBean> {

    @Override
    public int compare(StarBean o1, StarBean o2) {

        if (o1.getDistanceFromFilamentSpine() < o2.getDistanceFromFilamentSpine()) return -1;
        if (o1.getDistanceFromFilamentSpine() > o2.getDistanceFromFilamentSpine()) return 1;

        return 0;
    }
}
