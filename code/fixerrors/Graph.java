package fixerrors;

import city.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Graph {

    private ArrayList<ArrayList<Edge>> adj = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>(); // only for easy access
    private ArrayList<Edge> edges = new ArrayList<>(); // only for easy access

    public void addNode(Node n) {
        n.id = nodes.size();
        nodes.add(n);
        adj.add(new ArrayList<>());
    }

    public void addEdge(Edge edge) {
        if (edge.to.id == -1) {
            addNode(edge.to);
        }
        if (edge.from.id == -1) {
            addNode(edge.from);
        }
        edges.add(edge);

        adj.get(edge.from.id).add(edge);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    // code is copied from my previous implementation of dijkstra's which is in code/city/City.java
    public ArrayList<Node> getPath(Node from, Node to) {
        if (from.id == -1 || to.id == -1) {
            throw new IllegalArgumentException("Nodes don't belong to this Graph");
        }
        ArrayList<Node> path = new ArrayList<>();

        PriorityQueue<Edge> distances = new PriorityQueue<>(); // O(log n) insertion and removing iirc
        int[] distancesTo = new int[nodes.size()];
        boolean[] visited = new boolean[nodes.size()];

        int[] parents = new int[nodes.size()];

        Arrays.fill(distancesTo, Integer.MAX_VALUE);
        Arrays.fill(visited, false);

        // adding the current city as we know the distance from a city to itself is always 0
        distancesTo[from.id] = 0;
        distances.add(new Edge(from, from, 0));

        // calculations assuming V vertices (cities) and E edges (roads)
        for (int i = 0; i < nodes.size(); i++) { // O ( V )
            // if we no longer have any cities to look for, break
            if (distances.isEmpty()) break;
            // remove the city with the shortest distance to, this will be the final distance to that city
            Edge r = distances.remove(); // O ( log E )
            // if we have already visited that city we cross it out because we have already found the shortest
            // distance there
            if (visited[r.to.id]) continue;

            visited[r.to.id] = true; // mark it as visited to not set it to a different value
            parents[r.to.id] = r.from.id;

            for (Edge d : adj.get(r.to.id)) { // O ( E )
                // no need to look at stuff we have already determined the shortest distance to, we still need to
                // have a continue above because we may have a road to that city in the priority queue before they are
                // all removed.
                if (visited[d.to.id]) continue;

                // calculate the distance here so we do not have to copy this code inside the if statement
                int distanceTo = distancesTo[r.to.id] + d.cost;
                if (distanceTo < distancesTo[d.to.id]) {
                    // we have found a shorter distance to that city, now we add it to the priority queue and set it in
                    // our distanceTo array
                    distancesTo[d.to.id] = distanceTo;
                    distances.add(new Edge(d.from, d.to, distanceTo)); // O ( log E )
                }
            }
            // final time complexity: O ( V + E log E )? I remember reading that it is O (V + E log V) with priority queue.
            // I might have coded this wrong or got the wrong complexities.
        }

        Node id = to;
        int iters = 0;
        while (id.id != from.id && iters <= nodes.size() + 1) { // 2nd condition is to make sure that there are no loops.
            path.add(id);
            id = nodes.get(parents[id.id]);
            iters++;
        }
        path.add(from);

        // reverse cus ArrayList doesn't have this built in :(
        for (int i = 0; i < path.size() / 2; i++) {
            var temp = path.get(i);
            path.set(i, path.get(path.size() - i - 1));
            path.set(path.size() - i - 1, temp);
        }

        return path;
    }

    public static class Edge implements Comparable<Edge> {

        Node to, from;
        int cost;

        public Edge(Node from, Node to, int cost) {
            this.to = to;
            this.from = from;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    public static class Node {

        String name;
        private int id = -1;

        public Node(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph();

        var a = new Node("A");
        var b = new Node("B");
        var c = new Node("C");
        var d = new Node("D");

        graph.addNode(a);
        graph.addEdge(new Edge(a, b, 1));
        graph.addEdge(new Edge(a, c, 3));
        graph.addEdge(new Edge(c, d, 0));
        graph.addEdge(new Edge(b, d, 4));
        graph.addEdge(new Edge(a, d, 10));
        graph.addEdge(new Edge(a, b, 3));

        printPath(graph.getPath(a, d));
    }

    public static void printPath(ArrayList<Node> nodes) {
        for (var node : nodes) {
            System.out.println(node.name);
        }
    }
}