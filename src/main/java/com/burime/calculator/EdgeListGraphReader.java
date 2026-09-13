package com.burime.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class EdgeListGraphReader implements IGraphReader {

    @Override
    public List<Edge> loadEdgeList(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader не может быть null");
        }

        BufferedReader bufferedReader = (reader instanceof BufferedReader)
                ? (BufferedReader) reader
                : new BufferedReader(reader);

        List<Edge> edges = new ArrayList<>();
        String line = bufferedReader.readLine();
        if (line == null || line.trim().isEmpty()) {
            return edges;
        }

        int expectedCount = Integer.parseInt(line.trim());
        for (int i = 0; i < expectedCount; i++) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length >= 2) {
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                edges.add(new Edge(from, to));
            }
        }

        return edges;
    }
}