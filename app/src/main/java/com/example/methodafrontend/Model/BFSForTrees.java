package com.example.methodafrontend.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSForTrees {
    private final String ORPHAN = "[Orphan]";
    private final String FINAL = "[Final]";
    private final String INIT = "[Init]";
    private final Queue<Status> queue;
    static ArrayList<Status> nodes = new ArrayList<>();

    public BFSForTrees() {
        queue = new LinkedList<>();
    }

    public void PerformBFS(Status node, ArrayList<Status> statusArrayList) {
        for (Status status: statusArrayList
        ) {
            status.setVisited(false);
        }
        node.setRelation(INIT);
        nodes.clear();
        queue.add(node);
        node.setVisited(true);
        while (!queue.isEmpty()) {
            Status element = queue.remove();
            nodes.add(element);
            System.out.print(element.getStatusName() + "t");
            if(element != node && element.getNeighbours().size() != 0)
               element.setRelation("");
            List<Status> neighbours = element.getNeighbours();
            for (int i = 0; i < neighbours.size(); i++) {
                Status n = neighbours.get(i);
                if (n != null && !n.isVisited()) {
                    if(n.getNeighbours().size() == 0)
                    {
                        n.setRelation(FINAL);
                    }
                    else{
                        n.setRelation("");
                    }
                    queue.add(n);
                    n.setVisited(true);
                }
            }
        }
        for (Status status: statusArrayList
             ) {
            if(!status.isVisited())
                status.setRelation(ORPHAN);
        }
    }
}