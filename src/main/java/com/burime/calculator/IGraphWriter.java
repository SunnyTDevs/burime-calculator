package com.burime.calculator;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface IGraphWriter {
    void saveAdjacencyList(Writer writer, Map<Integer, List<Integer>> adjacencyList) throws IOException;
}