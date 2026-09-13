package com.burime.calculator;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public interface IGraphReader {
    List<Edge> loadEdgeList(Reader reader) throws IOException;
}