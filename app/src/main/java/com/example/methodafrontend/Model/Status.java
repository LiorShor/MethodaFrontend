package com.example.methodafrontend.Model;

import java.util.ArrayList;
import java.util.List;

public class Status {
    private final String statusName;
    private List<Status> neighbours;
    private boolean visited;

    public String getRelation() {
        return mRelation;
    }

    public void setRelation(String mRelation) {
        this.mRelation = mRelation;
    }

    private String mRelation;

    public Status(String statusName) {
        this.statusName = statusName;
        neighbours = new ArrayList<>();
        mRelation = "[Orphan]";
    }

    public String getStatusName() {
        return statusName;
    }

    public void AddNeighbours(Status neighbourNode)
    {
        this.neighbours.add(neighbourNode);
    }

    public List<Status> getNeighbours() {
        return neighbours;
    }
    public void setNeighbours(List<Status> neighbours) {
        this.neighbours = neighbours;
    }

    public void removeNeighbours(Status status){
        neighbours.remove(status);
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    @Override
    public String toString() {
        return statusName;
    }
}
