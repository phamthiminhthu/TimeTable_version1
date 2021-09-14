package com.it.phamthiminhthu.timetable.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NodeAllPath {
    private Subject subject;
    private List<Subject> visited;

}
