package com.simon.hw9;

import java.util.Comparator;

/**
 * Created by Simon on 11/27/16.
 */
public class SortComparator_State implements Comparator {
    public int compare(Object lhs, Object rhs) {
        Legislator a = (Legislator) lhs;
        Legislator b = (Legislator) rhs;
        if (a.getState().compareTo(b.getState()) > 0) {
            return 1;
        }
        else if (a.getState().compareTo(b.getState()) < 0) {
            return -1;
        }
        else {
            if (a.getLast_name().compareTo(b.getLast_name()) > 0) {
                return 1;
            }
            else if (a.getLast_name().compareTo(b.getLast_name()) < 0) {
                return -1;
            }
            else {
                if (a.getFirst_name().compareTo(b.getFirst_name()) > 0) {
                    return 1;
                }
                else if (a.getFirst_name().compareTo(b.getFirst_name()) < 0) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }
    }
}
