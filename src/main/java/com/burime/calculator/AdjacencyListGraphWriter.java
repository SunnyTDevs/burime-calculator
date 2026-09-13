package com.burime.calculator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraphWriter implements IGraphWriter {

    @Override
    public void saveAdjacencyList(Writer writer, Map<Integer, List<Integer>> adjacencyList) throws IOException {
        if (writer == null) {
            throw new IllegalArgumentException("Writer не может быть null");
        }

        BufferedWriter bufferedWriter = (writer instanceof BufferedWriter)
                ? (BufferedWriter) writer
                : new BufferedWriter(writer);

        if (adjacencyList != null) {
            for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(entry.getKey()).append(":");
                for (Integer neighbor : entry.getValue()) {
                    sb.append(" ").append(neighbor);
                }
                bufferedWriter.write(sb.toString());
                bufferedWriter.newLine();
            }
        }

        bufferedWriter.flush();
    }
}