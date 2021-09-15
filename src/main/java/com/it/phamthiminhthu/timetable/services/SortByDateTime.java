package com.it.phamthiminhthu.timetable.services;

import com.it.phamthiminhthu.timetable.entities.Subject;

import java.util.Comparator;

public class SortByDateTime implements Comparator<Subject> {
    @Override
    public int compare(Subject o1, Subject o2) {
        if(o1.getThu() > o2.getThu()) return o2.getThu() - o1.getThu();
        if(o1.getThu() == o2.getThu()){
            if(o1.getThoiGian().trim().compareTo(o2.getThoiGian().trim()) < 0){
                return -1;
            }else{
                return 1;
            }
        }
        return o1.getThu() - o2.getThu();
    }
}
