package com.burime.calculator;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

public class Graph {

    private IGraphReader reader;
    private IGraphWriter writer;

    private int[][] incidenceMatrix;
    private List<Integer> vertexList;
    private List<Edge> edgeList;

    public Graph(IGraphReader reader, IGraphWriter writer) {
        this.reader = reader;
        this.writer = writer;
        this.vertexList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
        this.incidenceMatrix = new int[0][0];
    }

    public IGraphReader getReader() {
        return reader;
    }

    public void setReader(IGraphReader reader) {
        this.reader = reader;
    }

    public IGraphWriter getWriter() {
        return writer;
    }

    public void setWriter(IGraphWriter writer) {
        this.writer = writer;
    }

    public int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    public List<Integer> getVertexList() {
        return Collections.unmodifiableList(vertexList);
    }

    public List<Edge> getEdgeList() {
        return Collections.unmodifiableList(edgeList);
    }

    public void load(Reader inputReader) throws IOException {
        if (reader == null) {
            throw new IllegalStateException("IGraphReader не установлен");
        }

        this.edgeList = reader.loadEdgeList(inputReader);

        // Сбор уникальных упорядоченных вершин
        Set<Integer> uniqueVertices = new TreeSet<>();
        for (Edge e : edgeList) {
            uniqueVertices.add(e.getFrom());
            uniqueVertices.add(e.getTo());
        }
        this.vertexList = new ArrayList<>(uniqueVertices);

        int vCount = vertexList.size();
        int eCount = edgeList.size();
        this.incidenceMatrix = new int[vCount][eCount];

        for (int eIdx = 0; eIdx < eCount; eIdx++) {
            Edge edge = edgeList.get(eIdx);
            int fromIdx = vertexList.indexOf(edge.getFrom());
            int toIdx = vertexList.indexOf(edge.getTo());

            if (fromIdx == toIdx) {
                // Петля
                incidenceMatrix[fromIdx][eIdx] = 2;
            } else {
                // Исходящее ребро: +1, входящее ребро: -1
                incidenceMatrix[fromIdx][eIdx] = 1;
                incidenceMatrix[toIdx][eIdx] = -1;
            }
        }
    }

    public void save(Writer outputWriter) throws IOException {
        if (writer == null) {
            throw new IllegalStateException("IGraphWriter не установлен");
        }

        Map<Integer, List<Integer>> adjacencyList = buildAdjacencyListFromIncidence();
        writer.saveAdjacencyList(outputWriter, adjacencyList);
    }

    public Map<Integer, List<Integer>> buildAdjacencyListFromIncidence() {
        Map<Integer, List<Integer>> adj = new LinkedHashMap<>();
        for (Integer v : vertexList) {
            adj.put(v, new ArrayList<>());
        }

        int vCount = vertexList.size();
        int eCount = edgeList.size();

        for (int eIdx = 0; eIdx < eCount; eIdx++) {
            int fromVertex = -1;
            int toVertex = -1;

            for (int vIdx = 0; vIdx < vCount; vIdx++) {
                if (incidenceMatrix[vIdx][eIdx] == 1) {
                    fromVertex = vertexList.get(vIdx);
                } else if (incidenceMatrix[vIdx][eIdx] == -1) {
                    toVertex = vertexList.get(vIdx);
                } else if (incidenceMatrix[vIdx][eIdx] == 2) {
                    fromVertex = vertexList.get(vIdx);
                    toVertex = vertexList.get(vIdx);
                }
            }

            if (fromVertex != -1 && toVertex != -1) {
                adj.get(fromVertex).add(toVertex);
            }
        }

        return adj;
    }

    public List<Integer> postOrderTraversal() {
        if (vertexList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Integer, List<Integer>> adj = buildAdjacencyListFromIncidence();
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        // Нахождение корня дерева (вершина без входящих рёбер)
        Set<Integer> incoming = new HashSet<>();
        for (List<Integer> neighbors : adj.values()) {
            incoming.addAll(neighbors);
        }

        Integer root = null;
        for (Integer v : vertexList) {
            if (!incoming.contains(v)) {
                root = v;
                break;
            }
        }
        if (root == null) {
            root = vertexList.get(0);
        }

        postOrderDfs(root, adj, visited, result);

        for (Integer v : vertexList) {
            if (!visited.contains(v)) {
                postOrderDfs(v, adj, visited, result);
            }
        }

        return result;
    }

    private void postOrderDfs(int current, Map<Integer, List<Integer>> adj,
                              Set<Integer> visited, List<Integer> result) {
        visited.add(current);
        List<Integer> neighbors = adj.getOrDefault(current, Collections.emptyList());
        for (int next : neighbors) {
            if (!visited.contains(next)) {
                postOrderDfs(next, adj, visited, result);
            }
        }
        result.add(current);
    }
}