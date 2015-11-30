package com.brynhildr.asgard.entities;

import java.util.Comparator;

/**
 * Created by ShuqinYe on 30/11/15.
 */
public class RelationComp implements Comparator<RelationWithID> {

    public int compare(RelationWithID r1, RelationWithID r2) {
        if (Integer.parseInt(r1.getPrimaryID()) > Integer.parseInt(r2.getPrimaryID())) return 1;
        else if (Integer.parseInt(r1.getPrimaryID()) < Integer.parseInt(r2.getPrimaryID())) return -1;
        else {
            return Integer.compare(Integer.parseInt(r1.getEventID()), Integer.parseInt(r2.getEventID()));
        }
    }

}
